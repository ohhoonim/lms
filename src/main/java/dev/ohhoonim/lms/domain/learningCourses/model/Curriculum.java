package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.List;

import javax.security.auth.Subject;

import dev.ohhoonim.lms.component.user.User.Manager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Curriculum {
    private Long id;
    private String curriculumName;
    private CurriculumRound curriculumRound;
    private Manager manager;
    private Integer times;
    private String learningTarget;
    private Boolean useYn;
    private List<Subject> subjects;
    private String contents;

    @Builder
    public Curriculum(Long id, String curriculumName, CurriculumRound curriculumRound, Manager manager,
            Integer times,
            String learningTarget, Boolean useYn, List<Subject> subjects, String contents) {
        this.id = id;
        this.curriculumName = curriculumName;
        this.curriculumRound = curriculumRound;
        this.manager = manager;
        this.times = times;
        this.learningTarget = learningTarget;
        this.useYn = useYn;
        this.subjects = subjects;
        this.contents = contents;
    }
}