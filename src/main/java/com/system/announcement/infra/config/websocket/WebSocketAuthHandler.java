package com.system.announcement.infra.config.websocket;

import com.system.announcement.infra.token.TokenService;
import com.system.announcement.services.AuthorizationService;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketAuthHandler extends TextWebSocketHandler {

    private final TokenService tokenService;
    private final AuthorizationService authorizationService;

    public WebSocketAuthHandler(TokenService tokenService, AuthorizationService authorizationService) {
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap((Message<?>) session.getAttributes());
        String token = accessor.getFirstNativeHeader("Authorization");

        if (token == null || token.isEmpty()) {
            System.out.println("Token ausente");
            session.close();
            return;
        }

        var email = tokenService.validateToken(token);

        if (email == null || email.isEmpty()) {
            System.out.println("Token inválido");
            session.close();
            return;
        }

        UserDetails user = authorizationService.loadUserByUsername(email);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("Usuário autenticado com sucesso: " + email);

        super.afterConnectionEstablished(session);
    }
}
