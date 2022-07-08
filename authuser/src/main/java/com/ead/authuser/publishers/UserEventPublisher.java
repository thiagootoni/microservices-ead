package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enuns.ActionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType){
        userEventDto.setActionType(actionType.toString());
        log.info("Send event {} user to exchange. UserId: {}", actionType.toString(), userEventDto.getUserId());
        rabbitTemplate.convertAndSend(exchangeUserEvent,"",userEventDto);
    }
}
