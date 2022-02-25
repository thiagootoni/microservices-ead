package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.enuns.UserStatus;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.exceptions.UnprocessableEntityException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AuthUserClient authUserClient;

    @Transactional
    @Override
    public void subscriptionIntoCourse(UUID courseId, SubscriptionDto subscriptionDto) {

        log.info("Started method subscribe user into course. userId: {}", subscriptionDto.getUserId());
        CourseModel course = courseService.findByIdAndFecthUsers(courseId);
        checkIfUserAlreadyIsASub(subscriptionDto, course);
        UserDto userDto = authUserClient.getById(subscriptionDto.getUserId()).getBody();
        checkIfUserIsBlocked(userDto);
        UserModel userModel = mapToUserModel(userDto);
        saveSubscriptionAndNotify(course, userModel);
        log.info("Finished method subscribe user into course");
    }

    private void checkIfUserAlreadyIsASub(SubscriptionDto subscriptionDto, CourseModel course) {
        if (course.getUsers().stream().anyMatch(user -> user.getId().equals(subscriptionDto.getUserId())))
            throw new UnprocessableEntityException("User already subscribed into course");
    }

    private void checkIfUserIsBlocked(UserDto userDto) {
        if (UserStatus.BLOCKED.equals(userDto.getUserStatus()))
            throw new UnprocessableEntityException("User is blocked");
    }

    private void saveSubscriptionAndNotify(CourseModel course, UserModel userModel) {
        course.getUsers().add(userModel);
        courseRepository.save(course);
        authUserClient.notifySubscribeUserIntoCourse(course.getId(), userModel.getId());
    }

    private UserModel mapToUserModel(UserDto userDto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return userModel;
    }
}
