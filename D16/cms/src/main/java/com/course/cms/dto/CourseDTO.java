package com.course.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Instructor is required")
    private String instructor;
}
