package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.UUID;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;

public interface SyllabusQuery {

    List<Professor> findProfessorList(String professorName);

    List<Assistant> findAssistantList(String assistantName);

    List<Syllabus> findSyllabusBySubject(UUID subjectId);

    boolean existsSyllabus(UUID syllabusId);

    Syllabus findSyllabusLectures(UUID syllabusId);
    
}
