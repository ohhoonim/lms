package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDateTime;
import java.util.Set;

import dev.ohhoonim.lms.component.user.Assistant;
import dev.ohhoonim.lms.component.user.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoundLecturePlan {
    private CurriculumRound curriculumRound;
    private Lecture lecture;
    private Set<Professor> professors;
    private Set<Assistant> assistants;
    private LocalDateTime lectureStartDateTime;
    private LocalDateTime lectureEndDateTime;

    @Builder
    public RoundLecturePlan(CurriculumRound curriculumRound, Lecture lecture, Set<Professor> professors,
            Set<Assistant> assistants, LocalDateTime lectureStartDateTime, LocalDateTime lectureEndDateTime) {
        this.curriculumRound = curriculumRound;
        this.lecture = lecture;
        this.professors = professors;
        this.assistants = assistants;
        this.lectureStartDateTime = lectureStartDateTime;
        this.lectureEndDateTime = lectureEndDateTime;
    }
}
