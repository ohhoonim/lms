package dev.ohhoonim.lms.curriculum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Subject;
import dev.ohhoonim.lms.curriculum.Curriculum.SubjectCondition;

@Service
public final class SubjectService implements Curriculum.Subject.Service {

    @Override
    public Subject save(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Subject> listSubject(SubjectCondition subjectCondition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listSubject'");
    }

}
