package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurriculumRound {
    private Long id;
    private String roundName;
    private Integer round;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public CurriculumRound(Long id, 
            String roundName, Integer round,
            LocalDate startDate, LocalDate endDate ) {
        this.id = id;
        this.roundName = roundName;
        this.round= round;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
