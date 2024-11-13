package dev.ohhoonim.lms.domain.learningCourses.model.exception;

public class AlreadyExists extends RuntimeException{
    public AlreadyExists(String message) {
        super(message);
    }
}
