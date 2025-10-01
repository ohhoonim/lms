package dev.ohhoonim.component.sign.api.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BearerAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var token = extractToken(request);

        if (token != null) {
            try {
                Authentication unauthenticatedToken = new BearerAuthenticationToken(token);
                Authentication authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);
                SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
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
