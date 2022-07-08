package com.ead.course.dtos;

import com.ead.course.enuns.UserStatus;
import com.ead.course.enuns.UserType;
import com.ead.course.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;

    public UserEventDto(UserModel userModel){
        BeanUtils.copyProperties(userModel, this);
        setUserId(userModel.getId());
        setUserStatus(userModel.getUserStatus().toString());
        setUserType(userModel.getUserType().toString());
    }

    public UserModel mapToUserModel(){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        userModel.setId(this.getUserId());
        userModel.setUserType(UserType.valueOf(this.getUserType()));
        userModel.setUserStatus(UserStatus.valueOf(this.getUserStatus()));
        return userModel;
    }

}
