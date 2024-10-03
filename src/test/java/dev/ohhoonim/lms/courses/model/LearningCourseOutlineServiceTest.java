package dev.ohhoonim.lms.courses.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.lms.courses.model.port.LearningCourseOutlineCommandPort;

@ExtendWith(MockitoExtension.class)
public class LearningCourseOutlineServiceTest {

    @InjectMocks
    private LearningCourseOutlineService service;

    @Mock
    private LearningCourseOutlineCommandPort commandPort;

    @Test
    public void addLearningCourseOutlineTest() {
        LearningCourseOutline learningCourseOutline = new LearningCourseOutline(
                null,
                "과정명",
                "목표",
                Set.of("student", "worker"),
                "컨텐츠",
                Set.of("self_study", "group_study"));
        service.addLearningCourseOutline(learningCourseOutline);
        verify(commandPort, times(1)).addLearningCourseOutline(any());
    }
}