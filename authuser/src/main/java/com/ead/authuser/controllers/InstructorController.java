package com.ead.authuser.controllers;

import com.ead.authuser.dtos.PromoteInstructorDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/instructors")
public class InstructorController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/subscription")
    public ResponseEntity<Object> promoteUserToInstructor(@RequestBody @Valid PromoteInstructorDto promoteInstructorDto){

        UserModel instructor = userService.promoteToInstructor(promoteInstructorDto);
        return ResponseEntity.ok(instructor);
    }

}
