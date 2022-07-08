package com.ead.notification.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCommandDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String message;
    private UUID userId;
}
