package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> , JpaSpecificationExecutor<ModuleModel> {

    List<ModuleModel> findByCourse(CourseModel course);

    //Page<ModuleModel> findAllByCourse(CourseModel course, Pageable pageable);

    @Query("Select module From ModuleModel module where module.course.id = ?1")
    Page<ModuleModel> findAllByCourse(UUID courseId, Specification<ModuleModel> spec,  Pageable pageable);

    Optional<ModuleModel> findByIdAndCourse(UUID modelId, CourseModel courseModel);

    @Query("Select module From ModuleModel module where (module.id = ?1 and module.course.id = ?2)")
    Optional<ModuleModel> findOneIntoCourse(UUID moduleId, UUID courseId);
}
