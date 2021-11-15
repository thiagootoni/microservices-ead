package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;

public interface AuthenticationService {

    UserModel singUp(UserDto userDto);
}
