package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
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
@RequestMapping(value = "/courses/{courseId}/modules")
public class ModuleController {

    @Autowired
    ModuleService service;

    @GetMapping
    public ResponseEntity<Page<ModuleModel>> getAllModulesIntoCourse(
            SpecificationTemplate.ModuleSpec spec, @PathVariable UUID courseId, @PageableDefault Pageable pageable){
        Page<ModuleModel> page = service.findAllIntoCourse(courseId, SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{moduleId}")
    public ResponseEntity<ModuleModel> getOneIntoCourse(@PathVariable UUID courseId, @PathVariable UUID moduleId){
        ModuleModel module = service.findOneIntoCourse(moduleId, courseId);
        return ResponseEntity.ok(module);
    }

    @PostMapping
    public ResponseEntity<ModuleModel> createModule(@PathVariable UUID courseId, @RequestBody @Valid ModuleDto moduleDto){
        ModuleModel module = service.createModule(moduleDto, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    @DeleteMapping(value = "/{moduleId}")
    public ResponseEntity<ModuleModel> deleteModuleWithLessons(@PathVariable UUID courseId, @PathVariable UUID moduleId){
        service.deleteModule(moduleId, courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{moduleId}")
    public ResponseEntity<ModuleModel> updateModule(@PathVariable UUID courseId, @PathVariable UUID moduleId, @RequestBody @Valid ModuleDto moduleDto){
        ModuleModel module = service.updateModule(moduleId, courseId, moduleDto);
        return ResponseEntity.ok(module);
    }
}
