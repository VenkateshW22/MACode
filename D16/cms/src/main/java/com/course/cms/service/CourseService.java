package com.course.cms.service;

import com.course.cms.dto.CourseDTO;
import com.course.cms.model.Course;
import com.course.cms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    private CourseDTO toDto(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setInstructor(course.getInstructor());
        return courseDTO;
    }

    private Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setInstructor(courseDTO.getInstructor());
        return course;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toDto).toList();
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = toEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return toDto(savedCourse);
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
        return courseRepository.findById(courseId)
                .map(existingCourse -> {
                    existingCourse.setTitle(courseDTO.getTitle());
                    existingCourse.setDescription(courseDTO.getDescription());
                    existingCourse.setInstructor(courseDTO.getInstructor());
                    Course updatedCourse = courseRepository.save(existingCourse);
                    return toDto(updatedCourse);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }
}
