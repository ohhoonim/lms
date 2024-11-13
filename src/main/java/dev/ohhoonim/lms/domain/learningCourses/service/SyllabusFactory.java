package dev.ohhoonim.lms.domain.learningCourses.service;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
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
    
}
