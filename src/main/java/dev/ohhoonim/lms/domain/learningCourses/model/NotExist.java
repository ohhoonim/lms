package dev.ohhoonim.lms.domain.learningCourses.model;

public class NotExist extends RuntimeException{
    public NotExist(String message) {
        super(message);
    }
}
