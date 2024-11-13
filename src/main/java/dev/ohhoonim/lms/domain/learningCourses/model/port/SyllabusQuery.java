package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;

public interface SyllabusQuery {

    List<Professor> findProfessorList(String professorName);

    List<Assistant> findAssistantList(String assistantName);
    
}
