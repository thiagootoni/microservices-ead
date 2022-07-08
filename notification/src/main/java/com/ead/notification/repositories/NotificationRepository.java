package com.ead.notification.repositories;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {

    Page<NotificationModel> findAllByUserIdAndNotificationStatusIsNotRead(UUID userId, Pageable pageable);

    Optional<NotificationModel> findByUserIdAndNotificationIdNotificationStatusIsNotRead(UUID userId, UUID notificationId);
}
