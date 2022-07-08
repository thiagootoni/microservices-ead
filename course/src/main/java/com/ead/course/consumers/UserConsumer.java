package com.ead.course.consumers;

import com.ead.course.dtos.UserEventDto;
import com.ead.course.enuns.ActionType;
import com.ead.course.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEvent}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEvent}", type = FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void userEventListener(@Payload UserEventDto userEventDto){
        var userModel = userEventDto.mapToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())){
            case CREATE, UPDATE -> userService.save(userModel);
            case DELETE -> userService.delete(userModel);
        }
    }
}
