package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.UUID;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;

public interface SyllabusUsecase {
    public void addLecture(Lecture lecture, UUID syllabusId);

    public List<Professor> findProfessor(String professorName);

    public List<Assistant> findAssistant(String assistantName);

    public List<Syllabus> findSyllabusBySubject(UUID subjectId);

    public void saveSyllabus(Syllabus syllabus, UUID subjectId);

    /**
     * lecture 정보도 담아서 넘겨야함
     * @param syllabusId
     * @return
     */
    public Syllabus findSyllabusLectures(UUID syllabusId);

    public void saveLecture(Lecture lecture);

}
