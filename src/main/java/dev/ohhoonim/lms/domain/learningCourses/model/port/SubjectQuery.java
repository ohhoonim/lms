package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface SubjectQuery {

    List<Subject> findSubjectList(Subject subject);

    Optional<Subject> findSubjectById(UUID subjectId);
    
}
