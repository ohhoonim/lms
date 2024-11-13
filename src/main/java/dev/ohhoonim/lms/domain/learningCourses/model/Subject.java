package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import dev.ohhoonim.lms.component.masterCode.CompletedStatus;
import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.component.user.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subject {
    private UUID id;
    private String subjectName;
    private Set<LectureMethod> lectureMethods;
    private Professor professor;
    private Syllabus syllabus;
    private LocalDate createDate; 
    private Boolean useYn;
    private CompletedStatus completed;

    @Builder
    public Subject(UUID id, String subjectName, Professor professor, Set<LectureMethod> lectureMethods,
            Syllabus syllabus, LocalDate createDate, Boolean useYn, CompletedStatus completed) {
        this.id = id;
        this.subjectName = subjectName;
        this.professor = professor;
        this.lectureMethods = lectureMethods;
        this.syllabus = syllabus;
        this.createDate = createDate;
        this.useYn = useYn;
        this.completed = completed;
    } 


    
}
