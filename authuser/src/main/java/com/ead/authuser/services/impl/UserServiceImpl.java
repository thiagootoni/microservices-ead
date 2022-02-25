package com.ead.authuser.services.impl;

import com.ead.authuser.controllers.UserController;
import com.ead.authuser.dtos.PromoteInstructorDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.exceptions.models.IdentityException;
import com.ead.authuser.exceptions.models.DataBaseException;
import com.ead.authuser.exceptions.models.ElementNotFoundException;
import com.ead.authuser.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public UserModel save(UserModel user) {
        return this.repository.save(user);
    }

    @Override
    public List<UserModel> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Page<UserModel> findAll(UUID courseId, Specification<UserModel> spec, Pageable pageable) {

        Page<UserModel> page;
        if (isNull(courseId)){
            page = this.repository.findAll(spec, pageable);
        }else{
            log.info("get users by courseId: {}", courseId);
            page = this.repository.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        }
        page.forEach(u -> addHATOAS(u));
        return page;
    }

    @Override
    public UserModel findOne(UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    @Override
    public UserModel findOneFetchCourses(UUID id) {
        return this.repository.findByIdAndFetchCourses(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    @Override
    public void deleteOne(UUID userId) {
        try {
            this.repository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("User not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Data integrity violated when trying to delete.");
        }
    }

    @Override
    public UserModel updateOne(UUID userId, UserDto userDto) {
        UserModel user = this.findOne(userId);
        mapIfPresent(userDto, user);
        return this.save(user);
    }

    private void mapIfPresent(UserDto userDto, UserModel user) {
        if (StringUtils.isNotBlank(userDto.getFullName()))
            user.setFullName(userDto.getFullName());

        if (StringUtils.isNotBlank(userDto.getPhoneNumber()))
            user.setPhoneNumber(userDto.getPhoneNumber());

        if (StringUtils.isNotBlank(userDto.getCpf()))
            user.setCpf(userDto.getCpf());
    }

    @Override
    public UserModel updatePassword(UUID userId, UserDto userDto) {
        UserModel user = this.findOne(userId);
        chekIfMatches(user.getPassword(), userDto.getOldPassword());
        user.setPassword(userDto.getPassword());
        return this.save(user);
    }

    private void chekIfMatches(String currentPassword, String oldPassword) {
        if (!currentPassword.equals(oldPassword))
            throw new IdentityException("Mismatched old password");
    }

    @Override
    public UserModel updateAvatar(UUID userId, UserDto userDto) {
        UserModel user = this.findOne(userId);
        user.setImageUrl(userDto.getImageUrl());
        return this.save(user);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return this.repository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    @Override
    public UserModel promoteToInstructor(PromoteInstructorDto promoteInstructorDto) {
        UserModel user = findOne(promoteInstructorDto.getUserId());
        user.setUserType(UserType.INSTRUCTOR);
        return repository.save(user);
    }

    private void addHATOAS(UserModel user) {
        user.add(linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel());
    }
}
