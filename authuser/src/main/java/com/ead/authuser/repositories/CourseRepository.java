package com.ead.authuser.repositories;

import com.ead.authuser.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
}
