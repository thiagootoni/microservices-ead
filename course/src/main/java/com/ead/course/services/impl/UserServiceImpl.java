package com.ead.course.services.impl;

import com.ead.course.dtos.NotificationCommandDto;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.enuns.UserStatus;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.exceptions.UnprocessableEntityException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.publisher.NotificationCommandPublisher;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationCommandPublisher notificationCommandPublisher;

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UserModel userModel) {
        this.userRepository.deleteById(userModel.getId());
    }

    @Override
    public Page<UserDto> getAllUsersIntoCourse(UUID courseId, Specification<UserModel> spec, Pageable pageable) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException("Course not Found"));

        return userRepository.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable)
                .map(userModel -> {
                    UserDto dto = new UserDto();
                    BeanUtils.copyProperties(userModel, dto);
                    return dto;
                });
    }

    @Transactional
    @Override
    public void subscriptionIntoCourse(UUID courseId, SubscriptionDto subscriptionDto) {

        log.info("Started method subscribe user into course. userId: {}", subscriptionDto.getUserId());
        UserModel user = userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new ElementNotFoundException("User not found"));        
        checkIfUserIsBlocked(user);
        CourseModel course = courseService.findByIdAndFecthUsers(courseId);
        checkIfUserAlreadyIsASub(user, course);
        saveSubscriptionAndSendNotification(course, user);
        log.info("Finished method subscribe user into course");
    }

    private void checkIfUserAlreadyIsASub(UserModel applicantUser, CourseModel course) {
        log.info("Check if user already subscribed");
        if (course.getUsers().stream().anyMatch(user -> user.getId().equals(applicantUser.getId())))
            throw new UnprocessableEntityException("User already subscribed into course");
    }

    private void checkIfUserIsBlocked(UserModel user) {
        log.info("Check if user is blocked");
        if (UserStatus.BLOCKED.equals(user.getUserStatus()))
            throw new UnprocessableEntityException("User is blocked");
    }

    private void saveSubscriptionAndSendNotification(CourseModel course, UserModel userModel) {
        log.info("Saving subscription");
        course.getUsers().add(userModel);
        courseRepository.save(course);

        try {
            var notification = NotificationCommandDto.of()
                    .title("Bem vindo ao curso " + userModel.getFullName())
                    .message("A sua inscrição foi realizada com sucesso!")
                    .userId(userModel.getId())
                    .build();
            notificationCommandPublisher.publishNotificationCommand(notification);

        }catch (Exception e){
            log.warn("Error sending notification - Broker can be off");
        }
    }
}
