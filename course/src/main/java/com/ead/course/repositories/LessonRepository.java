package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    List<LessonModel> findByModule(ModuleModel moduleModel);

    Optional<LessonModel> findByIdAndModule(UUID lessonId, ModuleModel module);

    Page<LessonModel> findAllByModule(ModuleModel moduleModel, Pageable pageable);
}
