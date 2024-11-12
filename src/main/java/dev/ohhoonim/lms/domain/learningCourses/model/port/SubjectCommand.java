package dev.ohhoonim.lms.domain.learningCourses.model.port;

import dev.ohhoonim.lms.domain.learningCourses.model.Subject;

public interface SubjectCommand {

    Subject saveSubject(Subject subject);
    
}
