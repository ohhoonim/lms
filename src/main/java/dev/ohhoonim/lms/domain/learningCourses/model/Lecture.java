package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import dev.ohhoonim.lms.component.masterCode.CompletedStatus;
import dev.ohhoonim.lms.component.masterCode.LectureMethod;

@Getter
@NoArgsConstructor
public class Lecture {
    private UUID id;
    private Integer lectureSequence;
    private String lectureTitle;
    private String lectureContents;
    private LectureMethod lectureMethod;
    private Integer lectureMinutes;
    private CompletedStatus completed;

    @Builder
    public Lecture(UUID id, Integer lectureSequence, String lectureTitle, String lectureContents, LectureMethod lectureMethod,
            Integer lectureMinutes, CompletedStatus completed) {
        this.id = id;
        this.lectureSequence = lectureSequence;
        this.lectureTitle = lectureTitle;
        this.lectureContents = lectureContents;
        this.lectureMethod = lectureMethod;
        this.lectureMinutes = lectureMinutes;
        this.completed = completed;
    }

}
