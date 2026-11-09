package Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final Service.JwtService jwt;
    public JwtAuthFilter(Service.JwtService jwt) { this.jwt = jwt; }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String h = req.getHeader("Authorization");
        if (h != null && h.startsWith("Bearer ")) {
            try {
                Claims c = jwt.ler(h.substring(7));
                if ("ADMIN".equals(c.get("papel", String.class))) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(c.getSubject(), null,
                                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
                }
            } catch (JwtException | IllegalArgumentException e) {
                // token inválido ou expirado: segue sem autenticar (rotas de admin respondem 401)
            }
        }
        chain.doFilter(req, res);
    }
}