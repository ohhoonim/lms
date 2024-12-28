package dev.ohhoonim.lms.curriculum.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Course;
import dev.ohhoonim.lms.curriculum.Curriculum.CourseSubject;
import dev.ohhoonim.lms.curriculum.Curriculum.Subject;

@Service
public final class CourseSubjectService implements Curriculum.CourseSubject.Service {

    @Override
    public List<Subject> addSubject(CourseSubject courseSubject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSubject'");
    }

    @Override
    public List<Subject> removeSubject(CourseSubject courseSubject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSubject'");
    }

    @Override
    public List<Subject> subjects(UUID courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subjects'");
    }

    @Override
    public List<Course> courses(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'courses'");
    }

}
