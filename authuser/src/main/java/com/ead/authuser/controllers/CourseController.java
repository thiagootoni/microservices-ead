package com.ead.authuser.controllers;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.SubscriptionDto;
import com.ead.authuser.services.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping(value = "/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable UUID userId){

        log.info("Started method getAllCoursesByUser {]", userId);
        Page<CourseDto> courses = courseService.getAllCoursesByUser(userId, pageable);
        log.info("Finished method getAllCoursesByUser {]", userId);
        return ResponseEntity.ok().body(courses);
    }

    @PostMapping(value = "/{userId}/courses/subscription")
    public ResponseEntity<Object> subscribeIntoCourse(
            @PathVariable UUID userId, @RequestBody @Valid SubscriptionDto subscriptionDto){

        courseService.subscribeUserIntoCourse(userId, subscriptionDto);
        return ResponseEntity.noContent().build();
    }
}
