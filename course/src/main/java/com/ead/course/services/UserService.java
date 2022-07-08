package com.ead.course.services;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.Valid;
import java.util.UUID;

public interface UserService {

    void subscriptionIntoCourse(UUID courseId, @Valid SubscriptionDto subscriptionDto);

    Page<UserDto> getAllUsersIntoCourse(UUID courseId, Specification<UserModel> spec, Pageable pageable);

    UserModel save(UserModel userModel);

    void delete(UserModel userModel);
}
