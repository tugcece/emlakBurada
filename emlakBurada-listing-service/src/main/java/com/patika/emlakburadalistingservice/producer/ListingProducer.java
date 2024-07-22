package com.patika.emlakburadalistingservice.producer;

import com.patika.emlakburadalistingservice.config.RabbitConfig;
import com.patika.emlakburadalistingservice.model.Listing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ListingProducer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitConfig rabbitConfig;

    public void sendListingForActivated(Listing listing) {
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKey(), listing);
        log.info("listing will be active. exchange:{}", rabbitConfig.getExchange());
    }

}