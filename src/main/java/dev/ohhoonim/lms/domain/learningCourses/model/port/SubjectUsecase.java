package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.UUID;

import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;

public interface SubjectUsecase {

    public Syllabus getSyllabusInSubject(UUID subjectId);

    public List<Professor> findProfessor(String professorName);

    /**
     * 과목 목록 조회
     * 필수 입력 : 과목명 
     * @param subject
     * @return
     */
    public List<Subject> findSubjectList(Subject subject);

    public Subject saveSubject(Subject subject);
}
