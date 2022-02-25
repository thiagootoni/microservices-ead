package com.ead.course.services;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuleService {

    ModuleModel findById(UUID moduleId);

    ModuleModel findOneIntoCourse(UUID courseId, UUID moduleId);

    ModuleModel createModule(ModuleDto moduleDto, UUID courseId);

    void deleteModule(UUID moduleId, UUID courseId);

    ModuleModel updateModule(UUID moduleId, UUID courseId, ModuleDto moduleDto);

    Page<ModuleModel> findAllIntoCourse(UUID courseId, Specification<ModuleModel> spec, Pageable pageable);
}
