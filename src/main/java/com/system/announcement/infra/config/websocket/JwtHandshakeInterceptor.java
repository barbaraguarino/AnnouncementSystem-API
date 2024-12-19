package com.system.announcement.infra.config.websocket;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.system.announcement.infra.token.TokenService;
import com.system.announcement.services.AuthorizationService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final TokenService tokenService;
    private final AuthorizationService authorizationService;

    public JwtHandshakeInterceptor(TokenService tokenService, AuthorizationService authorizationService) {
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getHeaders().getFirst("Authorization"); // Token enviado pelo cliente no cabeçalho
        if (token == null || token.isBlank()) {
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }

        try {
            String email = tokenService.validateToken(token.replace("Bearer ", ""));

            if (email.isEmpty()) {
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }

            attributes.put("userEmail", email); // Adiciona o email do usuário autenticado

            UserDetails user = authorizationService.loadUserByUsername(email);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Usuário autenticado via WebSocket: " + email);

            return true;
        } catch (JWTVerificationException e) {

            response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println("Erro inesperado no handshake WebSocket: " + e.getMessage());

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
