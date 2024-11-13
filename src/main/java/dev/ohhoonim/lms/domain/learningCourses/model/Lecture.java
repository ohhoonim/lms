package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import dev.ohhoonim.lms.component.masterCode.CompletedStatus;
import dev.ohhoonim.lms.component.masterCode.EvaluationMethod;
import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;

@Getter
@NoArgsConstructor
public class Lecture {
    private UUID id;
    private Integer lectureSequence;
    private String lectureTitle;
    private LectureMethod lectureMethod;
    private Integer numberOfLectureHours;
    private EvaluationMethod evaluationMethod;
    private String lectureContents;
    private Professor professor;
    private Assistant assistant;
    private CompletedStatus completed;

    @Builder
    public Lecture(UUID id, Integer lectureSequence, String lectureTitle, LectureMethod lectureMethod,
            Integer numberOfLectureHours, EvaluationMethod evaluationMethod, String lectureContents,
            Professor professor, Assistant assistant, CompletedStatus completed) {
        this.id = id;
        this.lectureSequence = lectureSequence;
        this.lectureTitle = lectureTitle;
        this.lectureMethod = lectureMethod;
        this.numberOfLectureHours = numberOfLectureHours;
        this.evaluationMethod = evaluationMethod;
        this.lectureContents = lectureContents;
        this.professor = professor;
        this.assistant = assistant;
        this.completed = completed;
    }



}
