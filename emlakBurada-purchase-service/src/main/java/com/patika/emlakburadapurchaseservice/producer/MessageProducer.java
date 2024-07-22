package com.patika.emlakburadapurchaseservice.producer;

import com.patika.emlakburadapurchaseservice.config.RabbitConfig;
import com.patika.emlakburadapurchaseservice.dto.request.UserBalanceUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class MessageProducer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitConfig rabbitConfig;

    public void sendMessage(UserBalanceUpdateRequest updateRequest) {
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getBalanceUpdateRoutingKey(), updateRequest);
        log.info("message sent for balance update. exchange:{}", rabbitConfig.getExchange());
    }

}