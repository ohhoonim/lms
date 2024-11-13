package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.InvalidParameters;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.NotFound;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusUsecase;

@Service
public class SyllabusService implements SyllabusUsecase {

    private final SyllabusCommand command;
    private final SyllabusQuery query;
    private final SubjectQuery subjectQuery;

    public SyllabusService(SyllabusCommand command, SyllabusQuery query,
            SubjectQuery subjectQuery) {
        this.command = command;
        this.query = query;
        this.subjectQuery = subjectQuery;
    }

    @Override
    @Transactional
    public void addLecture(Lecture lecture, UUID syllabusId) {
        if (notExistsSyllabus(syllabusId)) {
            throw new NotFound("존재하지 않는 과목 또는 계획서입니다");
        }
        command.addLecture(lecture, syllabusId);
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
    public List<Syllabus> findSyllabusBySubject(UUID subjectId) {
        if (notExistsSubject(subjectId)) {
            throw new InvalidParameters("과목은 필수입니다.");

        }
        return query.findSyllabusBySubject(subjectId);
    }

    @Override
    public void saveSyllabus(Syllabus syllabus, UUID subjectId) {
        if (notExistsSubject(subjectId)) {
            throw new InvalidParameters("과목이 존재하지 않습니다. ");
        }
        command.saveSyllabus(syllabus, subjectId);
    }

    private Boolean notExistsSubject(UUID subjectId) {
        return subjectId == null || !subjectQuery.existsSubject(subjectId);
    }

    private Boolean notExistsSyllabus(UUID syllabusId) {
        return syllabusId == null || !query.existsSyllabus(syllabusId);
    }

    @Override
    public Syllabus findSyllabusLectures(UUID syllabusId) {
        if (syllabusId == null) {
            throw new InvalidParameters("필수값이 누락되었습니다.");
        }
        return query.findSyllabusLectures(syllabusId);
    }

    @Override
    public void saveLecture(Lecture lecture) {
        command.saveLecture(lecture);
    }

}
