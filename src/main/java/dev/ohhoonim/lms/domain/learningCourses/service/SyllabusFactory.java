package dev.ohhoonim.lms.domain.learningCourses.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusQuery;

@Component
public class SyllabusFactory implements SyllabusCommand, SyllabusQuery {

    @Override
    public List<Professor> findProfessorList(String professorName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProfessorList'");
    }

    @Override
    public List<Assistant> findAssistantList(String assistantName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAssistantList'");
    }

    @Override
    public List<Syllabus> findSyllabusBySubject(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSyllabusBySubject'");
    }

    @Override
    public boolean existsSyllabus(UUID syllabusId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsSyllabus'");
    }

    @Override
    public void addLecture(Lecture lecture, UUID syllabusId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLecture'");
    }

    @Override
    public Object saveSyllabus(Syllabus syllabus, UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveSyllabus'");
    }

    @Override
    public Syllabus findSyllabusLectures(UUID syllabusId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSyllabusLectures'");
    }

    @Override
    public void saveLecture(Lecture lecture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveLecture'");
    }
    
}
