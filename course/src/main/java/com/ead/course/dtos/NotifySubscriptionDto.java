package com.ead.course.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
public class NotifySubscriptionDto {

    @NotBlank
    private UUID courseId;
}
