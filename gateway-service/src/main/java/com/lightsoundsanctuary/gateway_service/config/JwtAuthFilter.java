package com.lightsoundsanctuary.gateway_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String secret;

    // Shared secret proving a request originated from this gateway.
    // Downstream services reject any request that does not carry it.
    @Value("${gateway.internal-secret}")
    private String internalSecret;

    // Trust headers the gateway sets itself. Clients must never be able to
    // supply these, so we strip them from every inbound request.
    private static final String USER_HEADER = "X-User-Name";
    private static final String INTERNAL_AUTH_HEADER = "X-Internal-Auth";

    // Public routes that don't need a token
    private static final List<String> PUBLIC_ROUTES = List.of(
            "/api/users/register",
            "/api/users/login",
            "/api/users/oauth2",
            "/oauth2/",
            "/login/"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Strip any client-supplied trust headers and stamp the internal-auth
        // secret. This runs for EVERY request (public or not) so a client can
        // never spoof its identity or forge the gateway's origin proof.
        ServerWebExchange stamped = exchange.mutate()
                .request(r -> r.headers(headers -> {
                    headers.remove(USER_HEADER);
                    headers.remove(INTERNAL_AUTH_HEADER);
                    headers.add(INTERNAL_AUTH_HEADER, internalSecret);
                }))
                .build();

        // Allow public routes through without a token
        boolean isPublic = PUBLIC_ROUTES.stream()
                .anyMatch(path::startsWith);

        if (isPublic) {
            return chain.filter(stamped);
        }

        // Check Authorization header
        String authHeader = stamped.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            stamped.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return stamped.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(
                            secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Add the trusted user identity for downstream services.
            ServerWebExchange authed = stamped.mutate()
                    .request(r -> r.header(USER_HEADER, claims.getSubject()))
                    .build();

            return chain.filter(authed);

        } catch (Exception e) {
            stamped.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return stamped.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // Run before other filters
    }
}