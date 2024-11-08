package dev.ohhoonim.lms.domain.learningCourses.model;

public class NotFoundCurriculum extends RuntimeException{

    public NotFoundCurriculum(String message) {
        super(message);
    }

}
