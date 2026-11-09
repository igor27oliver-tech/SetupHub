package Controll;

import DTO.LoginRequest;
import DTO.LoginResponse;
import Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    private static final int MAX_FALHAS = 5;
    private static final long BLOQUEIO_MS = 5 * 60_000L;

    private final String adminLogin;
    private final String senhaHash;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final Map<String, long[]> falhas = new ConcurrentHashMap<>();   // [contagem, bloqueadoAte]

    public AuthController(@Value("${app.admin.login:admin}") String adminLogin,
                          @Value("${app.admin.senha:}") String senha,
                          PasswordEncoder encoder, JwtService jwt) {
        if (senha == null || senha.isBlank())
            throw new IllegalStateException("Defina a senha do admin na variável de ambiente APP_ADMIN_SENHA");
        this.adminLogin = adminLogin;
        this.encoder = encoder;
        this.senhaHash = encoder.encode(senha);
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletRequest http) {
        String cf = http.getHeader("CF-Connecting-IP");
        String chave = (cf != null && !cf.isBlank()) ? cf : http.getRemoteAddr();
        long agora = System.currentTimeMillis();

        long[] est = falhas.get(chave);
        if (est != null && est[1] > agora) {
            long seg = (est[1] - agora) / 1000 + 1;
            return ResponseEntity.status(429).header("Retry-After", String.valueOf(seg))
                    .body(Map.of("erro", "Muitas tentativas. Tente de novo em " + seg + " s"));
        }

        boolean loginOk = req != null && req.login() != null && adminLogin.equals(req.login().trim());
        boolean senhaOk = req != null && req.senha() != null && encoder.matches(req.senha(), senhaHash);

        if (!(loginOk && senhaOk)) {
            long[] e = falhas.computeIfAbsent(chave, k -> new long[]{0, 0});
            if (++e[0] >= MAX_FALHAS) { e[0] = 0; e[1] = agora + BLOQUEIO_MS; }
            return ResponseEntity.status(401).body(Map.of("erro", "Login ou senha inválidos"));
        }
        falhas.remove(chave);
        return ResponseEntity.ok(new LoginResponse(jwt.gerar(adminLogin), jwt.segundos(), adminLogin));
    }

    // o painel usa para saber se o token ainda vale
    @GetMapping("/admin/ping")
    public Map<String, String> ping(Authentication auth) {
        return Map.of("login", auth.getName());
    }
}