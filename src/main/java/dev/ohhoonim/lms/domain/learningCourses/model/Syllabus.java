package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import dev.ohhoonim.lms.component.masterCode.CompletedStatus;
import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.masterCode.UnitOfLecture;
import dev.ohhoonim.lms.component.user.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Syllabus {
    private UUID id;
    private String syllabusTitle;
    private transient Set<LectureMethod> lectureMethods; 
    private Integer numberOfLectureHours;
    private UnitOfLecture unitOfLecture;
    private Professor representativeProfessor;
    private transient CompletedStatus completed;
    private List<Lecture> lectures;

    @Builder
    public Syllabus(UUID id, String syllabusTitle, Set<LectureMethod> lectureMethods, Integer numberOfLectureHours,
            UnitOfLecture unitOfLecture, Professor representativeProfessor, CompletedStatus completed,
            List<Lecture> lectures) {
        this.id = id;
        this.syllabusTitle = syllabusTitle;
        this.lectureMethods = lectureMethods;
        this.numberOfLectureHours = numberOfLectureHours;
        this.unitOfLecture = unitOfLecture;
        this.representativeProfessor = representativeProfessor;
        this.completed = completed;
        this.lectures = lectures;
    }

    
}
