package com.ead.course.services;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LessonService {

    void deleteLessonsByModule(ModuleModel module);

    Page<LessonModel> findAllIntoModule(UUID moduleId, Pageable pageable);

    LessonModel findOneIntoModule(UUID moduleId, UUID lessonId);

    LessonModel createLesson(LessonDto lessonDto, UUID moduleId);

    void deleteLesson(UUID lessonId, UUID moduleId);

    LessonModel updateLesson(UUID lessonId, UUID moduleId, LessonDto lessonDto);
}
