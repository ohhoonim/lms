package dev.ohhoonim.lms.domain.learningCourses.model;

public class InvalidParameters extends RuntimeException {
    public InvalidParameters(String message) {
        super(message);
    }
}
