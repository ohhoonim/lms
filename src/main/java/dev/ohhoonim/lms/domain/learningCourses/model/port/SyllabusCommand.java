package dev.ohhoonim.lms.domain.learningCourses.model.port;

import java.util.UUID;

import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;

public interface SyllabusCommand {

    void addLecture(Lecture lecture, UUID syllabusId);

    Object saveSyllabus(Syllabus syllabus, UUID subjectId);

    void saveLecture(Lecture lecture);
    
}
