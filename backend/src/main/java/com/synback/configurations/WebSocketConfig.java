package com.synback.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration

// Habilita o processamento de mensagens WebSocket com o auxílio de um broker de
// mensagens. Ativar o suporte a WebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket");

        // Permite conexões de qualquer origem.
        // OBS: Em ambiente de produção deve se restringir as origens permitidas por
        // motivos de segurança
    }

    @Override
    
    // Este método configura o broker de mensagens
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // prefixo para os destinos de mensagens de broker
        registry.setApplicationDestinationPrefixes("/app"); // prefixo para filtrar destinos tratados por métodos
                                                            // anotados com @MessageMapping
    }
}