package dev.ohhoonim.lms.domain.learningCourses.model.port;

import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;

public interface CurriculumQuery {

    CurriculumRound getLastRound(Long id);
    
}
