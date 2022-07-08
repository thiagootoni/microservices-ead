package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.AuthenticationService;
import com.ead.authuser.services.UserService;
import com.ead.authuser.exceptions.models.IdentityException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    @Override
    public UserModel singUp(UserDto newUser) {
        checkForExistingSameCredentials(newUser);
        var user = mapToUserModel(newUser);
        return userService.saveAndSendEvent(user);
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
        return user;
    }
}
