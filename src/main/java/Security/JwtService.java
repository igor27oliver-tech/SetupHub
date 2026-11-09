package Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private final SecretKey key;
    private final long horas;

    public JwtService(@Value("${app.jwt.secret:}") String secret,
                      @Value("${app.jwt.horas:8}") long horas) {
        byte[] bytes;
        if (secret == null || secret.isBlank()) {
            bytes = new byte[48];
            new SecureRandom().nextBytes(bytes);
            log.warn("app.jwt.secret não definido: usando chave temporária (o admin precisará entrar de novo após reiniciar).");
        } else {
            bytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        if (bytes.length < 32) throw new IllegalStateException("app.jwt.secret precisa ter pelo menos 32 caracteres");
        this.key = Keys.hmacShaKeyFor(bytes);
        this.horas = horas;
    }

    public String gerar(String login) {
        Date agora = new Date();
        return Jwts.builder()
                .subject(login)
                .claim("papel", "ADMIN")
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + horas * 3_600_000L))
                .signWith(key)
                .compact();
    }

    public Claims ler(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public long segundos() { return horas * 3600; }
}