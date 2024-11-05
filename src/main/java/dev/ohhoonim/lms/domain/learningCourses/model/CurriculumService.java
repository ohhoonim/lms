package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;
import java.util.List;

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
    public Curriculum newRound(Curriculum prevCurriculum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newRound'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCurriculum'");
    }
}
