package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.time.LocalDate;
import java.util.List;

import dev.ohhoonim.lms.component.hollyday.Hollyday;
import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.RoundLecturePlan;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface CurriculumUsecase {
    public void addSubjectInCurriculum(Subject subject);

    public Subject findSubject(Subject subject);

    public Curriculum newRound(Curriculum prevCurriculum);

    public RoundLecturePlan generateplan();

    public List<Hollyday> getHollydays(LocalDate startDate, LocalDate endDate);

    public LocalDate calculateEndDate();

    public Curriculum saveCurriculum(Curriculum curriculum);
}
