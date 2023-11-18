package com.synback.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration

// Habilita o processamento de mensagens WebSocket com o auxílio de um broker de
// mensagens. Ativar o suporte a WebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Indica que o método está sobrescrevendo ou implementando um método de uma
    // superclasse ou interface.
    @Override

    // O método registerStompEndpoints cria um ponto específico (/websocket) no
    // servidor para conexões WebSocket, permitindo que os clientes se conectem e
    // comuniquem-se com o servidor.

    // STOMP (Simple Text Oriented Messaging Protocol) é um protocolo simples para
    // troca de mensagens, usado em aplicações WebSocket para facilitar a
    // comunicação entre cliente e servidor.
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket");
    }

    @Override

    // Um broker é um intermediário em sistemas de mensagens, que gerencia e roteia
    // as mensagens entre remetentes e destinatários, facilitando a comunicação e
    // garantindo a entrega eficiente das mensagens.

    // Este método configura o broker de mensagens no servidor
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // ativa um broker para gerenciar mensagens sob o prefixo "/topic"
        registry.setApplicationDestinationPrefixes("/app"); // prefixo para filtrar destinos tratados por métodos
                                                            // anotados com @MessageMapping

        // define "/app" como o prefixo para endereços que serão gerenciados por métodos
        // específicos do aplicativo
    }
}