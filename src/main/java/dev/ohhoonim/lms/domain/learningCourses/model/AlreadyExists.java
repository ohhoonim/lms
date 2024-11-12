package dev.ohhoonim.lms.domain.learningCourses.model;

public class AlreadyExists extends RuntimeException{
    public AlreadyExists(String message) {
        super(message);
    }
}
