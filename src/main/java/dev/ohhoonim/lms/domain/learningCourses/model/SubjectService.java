package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.User.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectUsecase;

@Service
public class SubjectService implements SubjectUsecase {

    private final SubjectCommand command;
    private final SubjectQuery query;

    public SubjectService(SubjectCommand command, SubjectQuery query) {
        this.command = command;
        this.query = query;
    }

    @Override
    @Transactional
    public void setSyllabus(Syllabus syllabus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSyllabus'");
    }

    @Override
    public Set<LectureMethod> getLectureMethods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLectureMethods'");
    }

    @Override
    public Syllabus getSyllabus(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSyllabus'");
    }

    @Override
    public List<Professor> findProfessor(String professorName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProfessor'");
    }

}
