package dev.ohhoonim.lms.domain.learningCourses.service;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumQuery;

@Component
public class CurriculumFactory implements CurriculumCommand, CurriculumQuery {

    @Override
    public Curriculum saveCurriculum(Curriculum curriculum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCurriculum'");
    }

    @Override
    public CurriculumRound getLastRound(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastRound'");
    }
    
}
