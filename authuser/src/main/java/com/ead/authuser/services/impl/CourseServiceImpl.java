package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.SubscriptionDto;
import com.ead.authuser.exceptions.models.UnprocessableEntityException;
import com.ead.authuser.models.CourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.CourseRepository;
import com.ead.authuser.services.CourseService;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        return courseClient.getAllCoursesByUser(userId, pageable);
    }

    @Override
    public void subscribeUserIntoCourse(UUID userId, SubscriptionDto subscriptionDto) {

        UserModel user = userService.findOneFetchCourses(userId);

        if (user.getCourses().stream().anyMatch(courseModel -> courseModel.getId().equals(subscriptionDto.getCourseId())))
            throw new UnprocessableEntityException("User already subscribe into course");

        CourseModel course = courseRepository.findById(subscriptionDto.getCourseId())
                .orElse(saveNewCourse(subscriptionDto.getCourseId()));
        user.getCourses().add(course);
        userService.save(user);
    }

    private CourseModel saveNewCourse(UUID courseId) {
        CourseModel course = new CourseModel();
        course.setId(courseId);
        return courseRepository.save(course);
    }
}
