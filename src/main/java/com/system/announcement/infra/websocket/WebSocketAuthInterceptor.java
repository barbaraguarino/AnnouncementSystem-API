package com.system.announcement.infra.websocket;

import com.system.announcement.infra.token.TokenService;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    public WebSocketAuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Extrai o token do cabeçalho "Authorization" (ou outra fonte)
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String token = accessor.getFirstNativeHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " do início
            String userEmail = tokenService.validateToken(token);

            if (!userEmail.isEmpty()) {
                // Cria um objeto Authentication com o usuário autenticado
                Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail, null, List.of());
                accessor.setUser(authentication); // Define o usuário no contexto da mensagem
            } else {
                throw new IllegalArgumentException("Token inválido");
            }
        }

        return message;
    }

}
