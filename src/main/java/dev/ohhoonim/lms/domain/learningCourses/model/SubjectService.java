package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.User.Professor;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectUsecase;

@Service
public class SubjectService implements SubjectUsecase {

    private final SubjectCommand command;
    private final SubjectQuery query;

    public SubjectService(SubjectCommand command, SubjectQuery query) {
        this.command = command;
        this.query = query;
    }

    @Override
    @Transactional
    public void setSyllabus(Syllabus syllabus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSyllabus'");
    }

    @Override
    public Syllabus getSyllabus(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSyllabus'");
    }

    @Override
    public List<Professor> findProfessor(String professorName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProfessor'");
    }

    @Override
    public List<Subject> findSubjectList(Subject subject) {
        if (subject == null || !StringUtils.hasText(subject.getSubjectName())
                || subject.getSubjectName().length() < 2) {
            throw new InvalidParameters("과목명은 필수로 입력해야합니다.");
        }
        List<Subject> result = query.findSubjectList(subject);

        return result.stream().map(distinctLectureMethod).toList();

    }

    private Function<Subject, Subject> distinctLectureMethod = s -> {
        if (s.getSyllabus() == null || s.getSyllabus().getLectures() == null) {
            return Subject.builder()
                    .id(s.getId())
                    .subjectName(s.getSubjectName())
                    .lectureMethods(null)
                    .professor(s.getProfessor())
                    .syllabus(s.getSyllabus())
                    .build();
        }

        Set<LectureMethod> lectureMethods = s.getSyllabus().getLectures().stream()
                .map(Lecture::getLectureMethod).distinct().collect(Collectors.toSet());

        return Subject.builder()
                .id(s.getId())
                .subjectName(s.getSubjectName())
                .lectureMethods(lectureMethods.stream()
                        .filter(m -> m != null)
                        .collect(Collectors.toSet()))
                .professor(s.getProfessor())
                .syllabus(s.getSyllabus())
                .build();
    };

    @Override
    public Subject saveSubject(Subject subject) {
        return command.saveSubject(subject);
    }
}
