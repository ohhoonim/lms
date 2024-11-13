package dev.ohhoonim.lms.domain.learningCourses.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;

@Component
public class SubjectFactory implements SubjectCommand, SubjectQuery {

    @Override
    public List<Subject> findSubjectList(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectList'");
    }

    @Override
    public Subject saveSubject(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveSubject'");
    }

    @Override
    public Optional<Subject> findSubjectById(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectById'");
    }

    @Override
    public boolean existsSubject(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsSubject'");
    }

}
