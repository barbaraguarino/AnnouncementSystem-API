package com.system.announcement.infra.config.websocket;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.system.announcement.infra.token.TokenService;
import com.system.announcement.services.AuthorizationService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final TokenService tokenService;

    public JwtHandshakeInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getURI().getQuery().replaceAll("token=", "");

        if (token.isBlank()) {
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }

        try {
            String email = tokenService.validateToken(token.replace("Bearer ", ""));

            if (email.isEmpty()) {
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }

            attributes.put("userEmail", email);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, null, null)
            );

            return true;
        } catch (JWTVerificationException e) {
            response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (exception == null) {
            System.out.println("Handshake bem-sucedido!");
        } else {
            System.err.println("Erro durante o handshake: " + exception.getMessage());
        }
    }
}
