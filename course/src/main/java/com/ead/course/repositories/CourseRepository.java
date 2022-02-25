package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query("SELECT course FROM CourseModel course LEFT JOIN FETCH course.users WHERE course.id = :courseId")
    Optional<CourseModel> findByIdAndFetchUsers(@Param("courseId") UUID courseId);
}
