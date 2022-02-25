package com.ead.course.services;

import com.ead.course.dtos.SubscriptionDto;

import javax.validation.Valid;
import java.util.UUID;

public interface CourseUserService {

    void subscriptionIntoCourse(UUID courseId, @Valid SubscriptionDto subscriptionDto);
}
