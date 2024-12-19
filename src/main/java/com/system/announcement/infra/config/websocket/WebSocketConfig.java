package com.system.announcement.infra.config.websocket;

import com.system.announcement.infra.token.TokenService;
import com.system.announcement.services.AuthorizationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final TokenService tokenService;
    private final AuthorizationService authorizationService;

    public WebSocketConfig(TokenService tokenService, AuthorizationService authorizationService) {
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic"); // Canal de mensagens
        config.setApplicationDestinationPrefixes("/app"); // Prefixo das mensagens enviadas pelo cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("*") // Permitir origens (ajustar em produção)
                .withSockJS() // Suporte a SockJS
                .setInterceptors(new JwtHandshakeInterceptor(tokenService, authorizationService)); // Interceptador JWT
    }
}
