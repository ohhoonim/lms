package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.UUID;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;

public interface CurriculumCommand {

    Curriculum saveCurriculum(Curriculum curriculum);

    void addSubjectInCurriculum(UUID subjectId, Long curriculumId);
    
}
