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

    /**
     * 커리큘럼 차수 추가 
     */
    public Curriculum newRound(Curriculum prevCurriculum);

    public RoundLecturePlan generateplan();

    public List<Hollyday> getHollydays(LocalDate startDate, LocalDate endDate);

    public LocalDate calculateEndDate();

    /**
     * 커리큘럼 신규 생성 또는 수정
     * id 존재여부로 신규 커리큘럼 여부를 결정한다. 
     * 신규인 경우 useYn은 true('사용함')으로 변경 지정되며,
     * 라운드 정보를 제공하지 않은 경우 '1차'수에 대한 기본 라운드 정보를 저장한다.
     * @param curriculum
     * @return
     */
    public Curriculum saveCurriculum(Curriculum curriculum);
}
