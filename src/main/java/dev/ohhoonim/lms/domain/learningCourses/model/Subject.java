package dev.ohhoonim.lms.domain.learningCourses.model;

import java.util.Set;
import java.util.UUID;

import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.User.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subject {
    private UUID id;
    private String subjectName;
    private Professor professor;
    private Set<LectureMethod> lectureMethods;
    private Syllabus syllabus;
    private Boolean useYn;

    @Builder
    public Subject(UUID id, String subjectName, Professor professor, Set<LectureMethod> lectureMethods,
            Syllabus syllabus, Boolean useYn) {
        this.id = id;
        this.subjectName = subjectName;
        this.professor = professor;
        this.lectureMethods = lectureMethods;
        this.syllabus = syllabus;
        this.useYn = useYn;
    } 


    
}
