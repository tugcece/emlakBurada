package com.patika.emlakburadauserbalanceservice.consumer;

import com.patika.emlakburadauserbalanceservice.dto.request.UserBalanceUpdateRequest;
import com.patika.emlakburadauserbalanceservice.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final UserBalanceService userBalanceService;
    @RabbitListener(queues = "balanceUpdateQueue")
    public void sendMessage(UserBalanceUpdateRequest userBalanceUpdateRequest) {
        userBalanceService.updateUserBalance( userBalanceUpdateRequest);
        log.info("balance updated.");
    }
}