package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusUsecase;

@Service
public class SyllabusService implements SyllabusUsecase {

    private final SyllabusCommand command;
    private final SyllabusQuery query;

    public SyllabusService(SyllabusCommand command, SyllabusQuery query) {
        this.command = command;
        this.query = query;
    }

    @Override
    @Transactional
    public void addLecture(Lecture lecture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLecture'");
    }

    @Override
    public List<Professor> findProfessor(String professorName) {
        return query.findProfessorList(professorName);
    }

    @Override
    public List<Assistant> findAssistant(String assistantName) {
        return query.findAssistantList(assistantName);
    }

    @Override
    public List<Subject> findSubjectByProfesser(Professor professor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectByProfesser'");
    }
    
}
