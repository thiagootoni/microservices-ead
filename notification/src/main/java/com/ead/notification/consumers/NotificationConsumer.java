package com.ead.notification.consumers;

import com.ead.notification.dtos.NotificationCommandDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.ead.notification.enums.NotificationStatus.CREATED;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class NotificationConsumer {

    @Autowired
    NotificationService notificationService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.notificationcommand.ms.notification}"),
            exchange = @Exchange(value = "${ead.notificationcommand}", type = TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ms.notification}")
    )
    public void listen(@Payload NotificationCommandDto notificationCommandDto){
        var notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(CREATED);
        notificationService.saveNotification(notificationModel);
    }
}
