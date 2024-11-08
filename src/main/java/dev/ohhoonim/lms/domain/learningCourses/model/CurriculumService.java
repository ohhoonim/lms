package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.component.hollyday.Hollyday;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumUsecase;

@Service
public class CurriculumService implements CurriculumUsecase {

    private final CurriculumQuery query;
    private final CurriculumCommand command;

    public CurriculumService(CurriculumQuery query, CurriculumCommand command) {
        this.query = query;
        this.command = command;
    }

    @Override
    @Transactional
    public void addSubjectInCurriculum(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSubjectInCurriculum'");
    }

    @Override
    public Subject findSubject(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubject'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateplan'");
    }

    @Override
    public List<Hollyday> getHollydays(LocalDate startDate, LocalDate endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHollydays'");
    }

    @Override
    public LocalDate calculateEndDate() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCurriculum'");
    }
}