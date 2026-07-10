package com.lightsoundsanctuary.santuary_map_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Rejects any request that did not arrive through the API gateway.
 *
 * The gateway stamps every forwarded request with a shared secret in the
 * X-Internal-Auth header. This service is not meant to be reachable directly,
 * so a request without the correct secret is refused. This also means the
 * gateway-set X-User-Name header can be trusted: a caller cannot forge it
 * without also knowing the secret.
 */
@Component
public class GatewayAuthFilter extends OncePerRequestFilter {

    private static final String INTERNAL_AUTH_HEADER = "X-Internal-Auth";

    @Value("${gateway.internal-secret}")
    private String internalSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!matches(request.getHeader(INTERNAL_AUTH_HEADER))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    // Constant-time comparison so the secret can't be recovered via timing.
    // Fail closed: a missing or unconfigured secret rejects the request.
    private boolean matches(String provided) {
        if (internalSecret == null || internalSecret.isBlank() || provided == null) {
            return false;
        }
        return MessageDigest.isEqual(
                internalSecret.getBytes(StandardCharsets.UTF_8),
                provided.getBytes(StandardCharsets.UTF_8));
    }
}
