package dev.ohhoonim.lms.domain.learningCourses.model.port;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;

public interface CurriculumCommand {

    Curriculum saveCurriculum(Curriculum curriculum);
    
}
