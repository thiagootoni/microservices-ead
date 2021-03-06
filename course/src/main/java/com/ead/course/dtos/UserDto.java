package com.ead.course.dtos;

import com.ead.course.enuns.UserStatus;
import com.ead.course.enuns.UserType;
import com.ead.course.models.UserModel;
import lombok.Data;


import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String userName;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;

}
