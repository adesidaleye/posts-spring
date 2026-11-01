package com.adesidaleye.blog.security;

import com.adesidaleye.blog.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Optional<String> token = extractToken(request);

            if (token.isPresent()) {
                UserDetails userDetails = authenticationService.validateToken(token.get());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

                // Validates the JWT, populates the SecurityContext, and exposes the user's UUID
                // as request attribute "userId" (avoids a DB lookup in every controller).
                if (userDetails instanceof BlogUserDetails blogUserDetails) {
                    request.setAttribute("userId", blogUserDetails.getId());
                }
            }
        } catch (Exception e) {
            // Don't throw exceptions, just don't authenticate
            log.warn("Received invalid auth token");
        }

        filterChain.doFilter(request, response);

    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String bearerToken = header.substring(7);
        return bearerToken.isBlank() ? Optional.empty() : Optional.of(bearerToken);
    }
}
