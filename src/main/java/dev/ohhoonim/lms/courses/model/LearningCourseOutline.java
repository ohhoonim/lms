package dev.ohhoonim.lms.courses.model;

import java.util.Set;

public record LearningCourseOutline(
                Long courseId,
                String courseName,
                String goal,
                Set<String> targets,
                String contents,
                Set<String> methods) {
}
