package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                          @PageableDefault(
                                                                                  page = 0,
                                                                                  size = 10,
                                                                                  sort = "creationDate",
                                                                                  direction = ASC)
                                                                                  Pageable pageable){

        var page = service.findAllNotificationByUser(userId, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> readNotification(@PathVariable(value = "userId") UUID userId,
                                                   @PathVariable(value="notificationId") UUID notificationId,
                                                   @RequestBody @Valid NotificationDto notificationDto){
        service.readNotification(userId, notificationId, notificationDto);
        return ResponseEntity.noContent().build();
    }

}
