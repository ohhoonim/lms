package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.component.hollyday.Hollyday;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumUsecase;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;

@Service
public class CurriculumService implements CurriculumUsecase {

    private final CurriculumQuery query;
    private final CurriculumCommand command;
    private final SubjectQuery subjectQuery;

    public CurriculumService(CurriculumQuery query, CurriculumCommand command,
            SubjectQuery subjectQuery) {
        this.query = query;
        this.command = command;
        this.subjectQuery = subjectQuery;
    }

    @Override
    @Transactional
    public void addSubjectInCurriculum(UUID subjectId, Long curriculumId) {

        Optional<Subject> subject = subjectQuery.findSubjectById(subjectId);
        if (subject.isEmpty()) {
            throw new NotExist("존재하지 않는 과목입니다");
        }

        List<Subject> subjects = query.findSubjectsInCurriculum(Subject.builder().build(), curriculumId);

        List<UUID> ids = subjects.stream().map(s -> s.getId())
                .filter(id -> id.equals(subjectId))
                .toList();
        if (ids != null && ids.size() != 0) {
            throw new AlreadyExists("이미 존재하는 과목입니다");
        }

        command.addSubjectInCurriculum(subjectId, curriculumId);
    }

    @Override
    public List<Subject> findSubject(Subject subject, Long curriculumId) {
        if (curriculumId == null || curriculumId == 0L) {
            throw new NotFoundCurriculum("curiculum id는 필수입니다.");
        }

        return query.findSubjectsInCurriculum(subject, curriculumId);
    }

    @Override
    @Transactional
    public Curriculum newRound(Long curriculumId) {
        Curriculum old = this.findCurriculum(curriculumId);
        CurriculumRound oldRound = old.getCurriculumRound();

        // 날짜는 복제안함
        var newRound = CurriculumRound.builder()
                .roundName(oldRound.getRoundName())
                .round(oldRound.getRound() + 1)
                .textbook(oldRound.getTextBook())
                .build();

        // 변경되는 사항 : 차수(자동계산), 교재
        var newCurriculum = Curriculum.builder()
                .curriculumName(old.getCurriculumName())
                .curriculumRound(newRound)
                .manager(old.getManager())
                .learningTarget(old.getLearningTarget())
                .useYn(true)
                .subjects(old.getSubjects())
                .contents(old.getContents())
                .build();

        return command.saveCurriculum(newCurriculum);
    }

    @Override
    @Transactional
    public RoundLecturePlan generateplan() {
        // TODO 시작일 입력하면 시수 체크해서 일정을 자동으로 생성하는 기능
        throw new UnsupportedOperationException("Unimplemented method 'generateplan'");
    }

    @Override
    public List<Hollyday> getHollydays(LocalDate startDate, LocalDate endDate) {
        // TODO 일정계획 수립시 휴일 정보를 가져오는 기능
        throw new UnsupportedOperationException("Unimplemented method 'getHollydays'");
    }

    @Override
    public LocalDate calculateEndDate() {
        // TODO 시수를 참고하여 종료일을 계산하는 기능
        throw new UnsupportedOperationException("Unimplemented method 'calculateEndDate'");
    }

    @Override
    @Transactional
    public Curriculum saveCurriculum(Curriculum curriculum) {
        var id = curriculum.getId();
        if (id == null || id == 0L) {
            curriculum.setUseYn(true);
            if (curriculum.getCurriculumRound() == null) {
                CurriculumRound newRound = CurriculumRound.builder()
                        .roundName("1차")
                        .build();
                curriculum.setCurriculumRound(newRound);
            }
        }

        return command.saveCurriculum(curriculum);
    }

    @Override
    public Curriculum findCurriculum(Long curriculumId) throws NotFoundCurriculum {
        Optional<Curriculum> curriculum = query.findCurriculum(curriculumId);
        if (curriculum.isEmpty()) {
            throw new NotFoundCurriculum(curriculumId + "에 대한 커리큘럼이 존재하지 않습니다.");
        }
        return curriculum.get();
    }

    @Override
    public List<Curriculum> findCurriculum(Curriculum filter) {
        // TODO 커리큘럼 목록을 조회하는 기능
        throw new UnsupportedOperationException("Unimplemented method 'findCurriculum'");
    }

}