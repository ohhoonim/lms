package dev.ohhoonim.lms.curriculum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.curriculum.Curriculum;
import dev.ohhoonim.lms.curriculum.Curriculum.Course;
import dev.ohhoonim.lms.curriculum.Curriculum.CourseCondition;

@Service
public final class CourseService implements Curriculum.Course.Service {

    @Override
    public Boolean isClosed(Course course) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isClosed'");
    }

    @Override
    public Course newRound(Course latestCourse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newRound'");
    }

    @Override
    public Course save(Course course) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Course> listCourse(CourseCondition courseCondition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listCourse'");
    }

}
