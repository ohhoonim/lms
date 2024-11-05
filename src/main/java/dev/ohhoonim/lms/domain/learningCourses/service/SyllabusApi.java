package dev.ohhoonim.lms.domain.learningCourses.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.lms.domain.learningCourses.model.port.SyllabusUsecase;

@RestController
@RequestMapping("/syllabus")
public class SyllabusApi {
    
    private final SyllabusUsecase service;

    public SyllabusApi(SyllabusUsecase service) {
        this.service = service;
    }
}
