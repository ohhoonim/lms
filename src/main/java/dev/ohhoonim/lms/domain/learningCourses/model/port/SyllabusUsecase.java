package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;

import dev.ohhoonim.lms.component.user.User.Assistant;
import dev.ohhoonim.lms.component.user.User.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;

public interface SyllabusUsecase {
    public void addLecture(Lecture lecture);

    public List<Professor> findProfessor(Professor professor);

    public List<Assistant> findAssistant(Assistant assistant);
}
