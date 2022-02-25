package com.ead.course.services.impl;

import com.ead.course.dtos.LessonDto;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository repository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleService moduleService;

    @Override
    public Page<LessonModel> findAllIntoModule(UUID moduleId, Pageable pageable) {
        ModuleModel module = moduleRepository.getById(moduleId);
        return repository.findAllByModule(module, pageable);
    }

    @Override
    public LessonModel findOneIntoModule(UUID moduleId, UUID lessonId) {
        ModuleModel module = moduleRepository.getById(moduleId);
        return repository.findByIdAndModule(lessonId,module)
                .orElseThrow(() -> new ElementNotFoundException("Lesson or Module not found"));
    }

    @Override
    public LessonModel createLesson(LessonDto lessonDto, UUID moduleId) {
        ModuleModel module = moduleService.findById(moduleId);
        LessonModel newLesson = new LessonModel();
        BeanUtils.copyProperties(lessonDto, newLesson);
        newLesson.setModule(module);
        return repository.save(newLesson);
    }

    @Override
    public void deleteLesson(UUID lessonId, UUID moduleId) {
        LessonModel lesson = findOneIntoModule(moduleId,lessonId);
        repository.delete(lesson);
    }

    @Override
    @Transactional
    public void deleteLessonsByModule(ModuleModel module) {
        List<LessonModel> lessons = repository.findByModule(module);
        for (LessonModel lesson: lessons) {
            repository.deleteById(lesson.getId());
        }
    }

    @Override
    public LessonModel updateLesson(UUID lessonId, UUID moduleId, LessonDto lessonDto) {
        LessonModel lesson = findOneIntoModule(moduleId, lessonId);
        BeanUtils.copyProperties(lessonDto, lesson);
        return repository.save(lesson);
    }
}
