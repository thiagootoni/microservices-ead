package com.ead.course.dtos;

import com.ead.course.enuns.CourseLevel;
import com.ead.course.enuns.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class CourseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "name cannot be null or empty.")
    private String name;

    @NotBlank(message = "description cannot be null or empty.")
    private String description;

    private String imageUrl;

    @NotNull(message = "courseStatus cannot be null or empty.")
    private CourseStatus courseStatus;

    @NotNull(message = "courseLevel cannot be null or empty.")
    private CourseLevel courseLevel;

    @NotNull(message = "userInstructor cannot be null or empty.")
    private UUID userInstructor;
}
