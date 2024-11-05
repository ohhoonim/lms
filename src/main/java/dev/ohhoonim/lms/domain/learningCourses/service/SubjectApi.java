package dev.ohhoonim.lms.domain.learningCourses.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectUsecase;

@RestController
@RequestMapping("/subject")
public class SubjectApi {
   
    private final SubjectUsecase service;

    public SubjectApi(SubjectUsecase service) {
        this.service = service;
    }
}
