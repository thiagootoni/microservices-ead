package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.AuthenticationService;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.IdentityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    @Override
    public UserModel singUp(UserDto newUser) {

        checkForExistingSameCredentials(newUser);
        var user = mapToUserModel(newUser);
        return userService.save(user);
    }

    private void checkForExistingSameCredentials(UserDto newUser) {
        if (userService.existsByUserName(newUser.getUserName()))
            throw new IdentityException("Username already exists.");

        if (userService.existsByEmail(newUser.getEmail()))
            throw new IdentityException("Email already exists.");
    }

    private UserModel mapToUserModel(UserDto newUser) {
        UserModel user = new ModelMapper().map(newUser, UserModel.class);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return user;
    }
}
