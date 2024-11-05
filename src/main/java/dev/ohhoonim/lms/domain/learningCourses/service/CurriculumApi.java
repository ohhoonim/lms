package dev.ohhoonim.lms.domain.learningCourses.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumUsecase;

@RestController
@RequestMapping("/curriculum")
public class CurriculumApi {
    
    private final CurriculumUsecase service;

    public CurriculumApi(CurriculumUsecase service) {
        this.service = service;
    }


}
