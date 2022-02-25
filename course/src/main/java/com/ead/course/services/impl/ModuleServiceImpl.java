package com.ead.course.services.impl;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository repository;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LessonService lessonService;

    @Override
    public ModuleModel findById(UUID moduleId){
        return repository.findById(moduleId)
                .orElseThrow(() -> new ElementNotFoundException("Module not found."));
    }

    @Override
    public ModuleModel findOneIntoCourse(UUID moduleId, UUID courseId) {
        /*return repository.findOneIntoCourse(moduleId,courseId)
                .orElseThrow(() -> new ElementNotFoundException("Module or Course not found"));
        */

        CourseModel c = courseRepository.getById(courseId);
        return repository.findByIdAndCourse(moduleId,c)
                .orElseThrow(() -> new ElementNotFoundException("Module or Course not found"));
    }

    @Override
    public Page<ModuleModel> findAllIntoCourse(UUID courseId, Specification<ModuleModel> spec, Pageable pageable) {
        return repository.findAllByCourse(courseId, spec, pageable);
    }

    @Override
    public ModuleModel createModule(ModuleDto moduleDto, UUID courseId) {
        CourseModel course = courseService.findById(courseId);
        ModuleModel module = new ModuleModel();
        BeanUtils.copyProperties(moduleDto,module);
        module.setCourse(course);
        return repository.save(module);
    }

    @Override
    @Transactional
    public ModuleModel updateModule(UUID moduleId, UUID courseId, ModuleDto moduleDto) {
        ModuleModel module = findOneIntoCourse(moduleId, courseId);
        BeanUtils.copyProperties(moduleDto, module);
        return repository.save(module);
    }

    @Override
    @Transactional
    public void deleteModule(UUID moduleId, UUID courseId) {
        ModuleModel module = findOneIntoCourse(moduleId, courseId);
        lessonService.deleteLessonsByModule(module);
        repository.deleteById(module.getId());
    }
}
