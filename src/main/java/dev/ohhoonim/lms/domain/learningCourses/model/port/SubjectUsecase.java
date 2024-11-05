package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.Set;

import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.User.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;

public interface SubjectUsecase {
    public void setSyllabus(Syllabus syllabus);

    public Set<LectureMethod> getLectureMethods();

    public Syllabus getSyllabus(Subject subject);

    public List<Professor> findProfessor(String professorName);
}
