package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.Optional;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;

public interface CurriculumQuery {

    CurriculumRound getLastRound(Long id);

    Optional<Curriculum> findCurriculum(Long curriculumId);
    
}
