package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Syllabus {
    private UUID id;
    private String syllabusTitle;
    private List<Lecture> lectures;

    public Syllabus(UUID id, String syllabusTitle, List<Lecture> lectures) {
        this.id = id;
        this.syllabusTitle = syllabusTitle;
        this.lectures = lectures;
    }
}
