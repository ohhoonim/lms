package dev.ohhoonim.lms.domain.learningCourses.model.exception;

public class NotExist extends RuntimeException{
    public NotExist(String message) {
        super(message);
    }
}
