package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import dev.ohhoonim.lms.component.hollyday.Hollyday;
import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.RoundLecturePlan;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface CurriculumUsecase {
    public Curriculum findCurriculum(Long id);

    public List<Curriculum> findCurriculum(Curriculum filter);

    /**
     * 커리큘럼에 과목을 등록한다. 
     * 등록 후 목록 조회는 findSubject를 호출 
     * @param subject
     * @param curriculumId
     */
    public void addSubjectInCurriculum(UUID subject, Long curriculumId);

    /**
     * 커리큘럼의 과목 목록을 조회한다  
     * @param subjectFilter
     * @param curriculumId
     * @return
     */
    public List<Subject> findSubject(Subject subjectFilter, Long curriculumId);

    /**
     * 동일 학습과정에 차수만 추가한다. 일종의 복제 기능 
     * 차수만 +1되어 신규 학습과정을 생성하고 과목 목록도 복제된다.  
     * 일정생성은 generateplan 으로 별도 생성  
     * @param curriculumId
     * @return
     */
    public Curriculum newRound(Long curriculumId);

    public RoundLecturePlan generateplan();

    public List<Hollyday> getHollydays(LocalDate startDate, LocalDate endDate);

    public LocalDate calculateEndDate();

    /**
     * 커리큘럼 신규 생성 또는 수정
     * id 존재여부로 신규 커리큘럼 여부를 결정한다.
     * 신규인 경우 useYn은 true('사용함')으로 변경 지정되며,
     * 라운드 정보를 제공하지 않은 경우 '1차'수에 대한 기본 라운드 정보를 저장한다.
     * 
     * @param curriculum
     * @return
     */
    public Curriculum saveCurriculum(Curriculum curriculum);
}
