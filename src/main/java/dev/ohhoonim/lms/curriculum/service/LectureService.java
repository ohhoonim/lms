package dev.ohhoonim.lms.curriculum.service;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Lecture;

@Service
public final class LectureService implements Curriculum.Lecture.Service{

    @Override
    public Lecture modify(Lecture lecture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

}
