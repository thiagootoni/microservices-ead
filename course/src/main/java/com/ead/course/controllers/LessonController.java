package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/modules/{moduleId}/lessons")
public class LessonController {

    @Autowired
    LessonService service;

    @GetMapping
    public ResponseEntity<Page<LessonModel>> getAllLessonsIntoModule(
            @PathVariable UUID moduleId, @PageableDefault Pageable pageable){
        Page<LessonModel> page = service.findAllIntoModule(moduleId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{lessonId}")
    public ResponseEntity<LessonModel> getOneIntoModule(@PathVariable UUID moduleId, @PathVariable UUID lessonId){
        LessonModel lesson = service.findOneIntoModule(moduleId, lessonId);
        return ResponseEntity.ok(lesson);
    }

    @PostMapping
    public ResponseEntity<LessonModel> createLesson(@PathVariable UUID moduleId, @RequestBody @Valid LessonDto lessonDto){
        LessonModel lesson = service.createLesson(lessonDto, moduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }

    @DeleteMapping(value = "/{lessonId}")
    public ResponseEntity<LessonModel> deleteLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId){
        service.deleteLesson(lessonId, moduleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{lessonId}")
    public ResponseEntity<LessonModel> updateLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId, @RequestBody @Valid LessonDto lessonDto){
        LessonModel module = service.updateLesson(lessonId, moduleId, lessonDto);
        return ResponseEntity.ok(module);
    }
}
