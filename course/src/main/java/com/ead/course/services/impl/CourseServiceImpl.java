package com.ead.course.services.impl;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.services.LessonService;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonService lessonService;

    @Override
    public CourseModel findById(UUID courseId) {
        return repository.findById(courseId)
                .orElseThrow(() -> new ElementNotFoundException("Course not Found"));
    }

    @Override
    public CourseModel findByIdAndFecthUsers(UUID courseId) {
        return repository.findByIdAndFetchUsers(courseId)
                .orElseThrow(()-> new ElementNotFoundException("Course not found"));
    }

    @Override
    public Page<CourseModel> findAll(UUID userId, Specification<CourseModel> spec, Pageable page) {

        if (nonNull(userId)){
            return repository.findAll(SpecificationTemplate.courseUserId(userId).and(spec), page);
        }else{
            return repository.findAll(spec, page);
        }
    }

    @Override
    public CourseModel createCourse(CourseDto courseDto) {
        CourseModel newCourse = mapToCourseModel(courseDto);
        newCourse.setCreationDate(LocalDateTime.now(ZoneId.of("UTC-3")));
        return repository.save(newCourse);
    }

    @Override
    public CourseModel updateCourse(CourseDto courseDto, UUID courseId) {
        CourseModel updateCourse = findById(courseId);
        BeanUtils.copyProperties(courseDto, updateCourse);
        return repository.save(updateCourse);
    }

    @Transactional
    @Override
    public void deleteCourse(UUID courseId) {

        CourseModel course = findById(courseId);
        // teste
        // course.getModules(); // testar e remover
        deleteModulesByCourse(course);
        repository.deleteById(course.getId());
    }

    private void deleteModulesByCourse(CourseModel course) {
        List<ModuleModel> modules = moduleRepository.findByCourse(course);
        for (ModuleModel module: modules) {
            lessonService.deleteLessonsByModule(module);
            moduleRepository.deleteById(module.getId());
        }
    }

    private CourseModel mapToCourseModel(CourseDto courseDto) {
        CourseModel newCourse = new CourseModel();
        BeanUtils.copyProperties(courseDto, newCourse);
        return newCourse;
    }
}
