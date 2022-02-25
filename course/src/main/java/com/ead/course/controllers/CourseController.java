package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<CourseModel> getCourseById(@PathVariable UUID courseId){
        CourseModel course = service.findById(courseId);
        return ResponseEntity.ok().body(course);
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(
            SpecificationTemplate.CourseSpec spec,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) UUID userId){

        Page<CourseModel> page = service.findAll(userId, spec, pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    public ResponseEntity<CourseModel> createCourse(@RequestBody @Valid CourseDto courseDto){
        CourseModel newCourse = service.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    @PutMapping(value = "/{courseId}")
    public ResponseEntity<CourseModel> updateCourse(@PathVariable UUID courseId, @RequestBody @Valid CourseDto courseDto){
        CourseModel updatedCourse = service.updateCourse(courseDto, courseId);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<CourseModel> deleteCourse(@PathVariable UUID courseId){
        service.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
