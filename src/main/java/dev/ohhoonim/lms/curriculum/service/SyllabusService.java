package dev.ohhoonim.lms.curriculum.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Lecture;
import dev.ohhoonim.lms.curriculum.Curriculum.Syllabus;

@Service
public final class SyllabusService implements Curriculum.Syllabus.Service {

    @Override
    public Syllabus save(Syllabus syllabus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Set<String> lecturesMethods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lecturesMethods'");
    }

    @Override
    public List<Lecture> addLecture(Lecture lecture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLecture'");
    }

    @Override
    public List<Lecture> removeLecture(Long lecture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLecture'");
    }

    @Override
    public List<Lecture> lectures(UUID syllabusid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lectures'");
    }

}
