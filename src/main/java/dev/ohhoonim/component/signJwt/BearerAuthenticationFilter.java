package dev.ohhoonim.component.signJwt;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BearerAuthenticationFilter extends OncePerRequestFilter {

    private final BearerTokenUsecase bearerTokenService;
    private final AuthorityPort authorityPort;

    public BearerAuthenticationFilter(BearerTokenUsecase bearerTokenService,
            AuthorityPort authorityPort
    ) {
        this.bearerTokenService = bearerTokenService;
        this.authorityPort = authorityPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var token = extractToken(request);

        if (token != null) {
            try {
                var username = bearerTokenService.getUsername(token);
                var authorities = authorityPort.authoritiesByUsername(username);

                var authentication = new BearerAuthenticationToken(authorities);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new RuntimeException("유효하지 않은 토큰입니다");
            }

        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
