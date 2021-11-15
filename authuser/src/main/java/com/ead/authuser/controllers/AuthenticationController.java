package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.services.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping( "/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService service;

    @PostMapping( "/signup")
    public ResponseEntity<Object> singUp(
            @JsonView(UserDto.UserView.RegistrationPost.class) @RequestBody UserDto newUser){
        var user = this.service.singUp(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


}
