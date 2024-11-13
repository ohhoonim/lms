package dev.ohhoonim.lms.domain.learningCourses.model.exception;

public class InvalidParameters extends RuntimeException {
    public InvalidParameters(String message) {
        super(message);
    }
}
