package dev.ohhoonim.lms.domain.learningCourses.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurriculumRound {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String roundName;

    @Builder
    public CurriculumRound(Long id, LocalDate startDate, LocalDate endDate, String roundName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roundName = roundName;
    }
}
