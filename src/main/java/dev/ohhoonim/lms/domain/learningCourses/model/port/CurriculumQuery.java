package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.List;
import java.util.Optional;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface CurriculumQuery {

    CurriculumRound getLastRound(Long id);

    Optional<Curriculum> findCurriculum(Long curriculumId);

    List<Subject> findSubjectsInCurriculum(Subject subject, Long curriculumId);
    
}
