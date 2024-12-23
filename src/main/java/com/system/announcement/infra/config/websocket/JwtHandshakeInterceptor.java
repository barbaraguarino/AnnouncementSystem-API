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
    private final AuthorizationService authorizationService;

    public JwtHandshakeInterceptor(TokenService tokenService, AuthorizationService authorizationService) {
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Entrou no Before Handshake");

        System.out.println("Query recebidas: " + request.getURI().getQuery());

        String token = request.getURI().getQuery().replaceAll("token=", "");

        System.out.println("Token recebido na query: " + token);

        if (token.isBlank()) {
            System.out.println("Token nao encontrado");
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }

        try {

            System.out.println("Token encontrado");

            String email = tokenService.validateToken(token.replace("Bearer ", ""));

            if (email.isEmpty()) {
                System.out.println("Email nao encontrado");
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }

            System.out.println("Email encontrado: " + email);

            attributes.put("userEmail", email);

            System.out.println("Usuário autenticado via WebSocket: " + email);

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
