package com.ead.notification.services;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationModel saveNotification(NotificationModel notificationModel){
        return notificationRepository.save(notificationModel);
    }

    public Page<NotificationModel> findAllNotificationByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndNotificationStatusIsNotRead(userId, pageable);
    }

    public void readNotification(UUID userId, UUID notificationId, NotificationDto notificationDto) {
        var notification =
                notificationRepository.findByUserIdAndNotificationIdNotificationStatusIsNotRead(userId, notificationId)
                        .orElseThrow(() -> new IllegalArgumentException("Not found notification for this user"));
        notification.setNotificationStatus(notificationDto.getStatus());
        notificationRepository.save(notification);
    }
}
