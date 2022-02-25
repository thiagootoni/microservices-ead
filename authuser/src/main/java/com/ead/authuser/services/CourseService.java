package com.ead.authuser.services;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.SubscriptionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CourseService {

    Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable);

    void subscribeUserIntoCourse(UUID userId, SubscriptionDto subscriptionDto);
}
