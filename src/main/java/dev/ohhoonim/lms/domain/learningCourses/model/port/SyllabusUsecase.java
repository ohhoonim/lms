package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface SyllabusUsecase {
    public void addLecture(Lecture lecture);

    public List<Professor> findProfessor(String professorName);

    public List<Assistant> findAssistant(String assistantName);

    public List<Subject> findSubjectByProfesser(Professor professor);
}
