package com.ead.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LessonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "title cannot be null or empty.")
    private String title;

    @NotBlank(message = "description cannot be null or empty.")
    private String description;

    private String videoUrl;
}
