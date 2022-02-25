package com.ead.authuser.services;

import com.ead.authuser.dtos.PromoteInstructorDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Page<UserModel> findAll(UUID courseId, Specification<UserModel> spec, Pageable pageable);

    UserModel findOne(UUID id);

    UserModel findOneFetchCourses(UUID id);

    UserModel save(UserModel user);

    void deleteOne(UUID userId);

    UserModel updateOne(UUID userId, UserDto userDto);

    UserModel updatePassword(UUID userId, UserDto userDto);

    UserModel updateAvatar(UUID userId, UserDto userDto);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    UserModel promoteToInstructor(PromoteInstructorDto promoteInstructorDto);
}
