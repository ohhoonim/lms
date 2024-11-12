package dev.ohhoonim.lms.domain.learningCourses.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
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

    @Override
    public Optional<Curriculum> findCurriculum(Long curriculumId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCurriculum'");
    }

    @Override
    public List<Subject> findSubjectsInCurriculum(Subject subject, Long curriculumId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectsInCurriculum'");
    }

    @Override
    public void addSubjectInCurriculum(UUID subject, Long curriculumId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSubjectInCurriculum'");
    }
    
}
