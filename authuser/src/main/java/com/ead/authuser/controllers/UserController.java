package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUser(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserModel> page = service.findAll(spec, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserModel> getOne(@PathVariable(value = "userId") UUID userId) {
        UserModel user = this.service.findOne(userId);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = {"/{userId}"})
    public ResponseEntity<UserModel> deleteOne(@PathVariable(value = "userId") UUID userId) {
        this.service.deleteOne(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @JsonView(UserDto.UserView.UserPut.class) @RequestBody UserDto userDto) {

        UserModel user = this.service.updateOne(userId, userDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
    }

    @PutMapping(value = "/{userId}/password")
    public ResponseEntity<UserModel> updatePasswordUser(@PathVariable(value = "userId") UUID userId,
                                                     @JsonView(UserDto.UserView.PasswordPut.class) @RequestBody UserDto userDto) {
        this.service.updatePassword(userId, userDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{userId}/avatar")
    public ResponseEntity<Object> updateAvatarUser(@PathVariable(value = "userId") UUID userId,
                                                   @JsonView(UserDto.UserView.ImagePut.class) @RequestBody UserDto userDto) {
        this.service.updateAvatar(userId, userDto);
        return ResponseEntity.noContent().build();
    }


}
