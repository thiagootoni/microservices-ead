package com.ead.course.services;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

public interface CourseService {

    CourseModel findById(UUID courseId);

    CourseModel findByIdAndFecthUsers(UUID courseId);

    Page<CourseModel> findAll(UUID courseId, Specification<CourseModel> spec, Pageable pageable);

    CourseModel createCourse(CourseDto courseDto);

    CourseModel updateCourse(CourseDto courseDto, UUID courseId);

    void deleteCourse(UUID courseId);
}
