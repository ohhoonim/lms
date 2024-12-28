package dev.ohhoonim.lms.curriculum.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Syllabus;

@Service
public final class SubjectSyllabusService implements Curriculum.SubjectSyllabus.Service {

    @Override
    public List<Syllabus> syllabues(UUID subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'syllabues'");
    }

    @Override
    public List<Syllabus> addSyllabus(UUID subjectId, UUID syllabusId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSyllabus'");
    }

    @Override
    public List<Syllabus> removeSyllabus(UUID subjectSyllabusId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSyllabus'");
    }

}
