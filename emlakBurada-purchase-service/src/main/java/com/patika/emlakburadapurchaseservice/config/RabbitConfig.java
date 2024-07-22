package com.patika.emlakburadapurchaseservice.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitConfig {

    @Value(value = "${balance.update.queue}")
    private String balanceUpdateQueue;

    @Value(value = "${exchange}")
    private String exchange;

    @Value(value = "${balance.update.routingkey}")
    private String balanceUpdateRoutingKey;

    @Bean
    public Queue balanceUpdateQueue() {
        return new Queue(balanceUpdateQueue);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding balanceUpdateBinding(Queue balanceUpdateQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(balanceUpdateQueue)
                .to(exchange)
                .with(balanceUpdateRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
