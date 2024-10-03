package dev.ohhoonim.lms.courses.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.courses.model.port.LearningCourseOutlineCommandPort;
import lombok.RequiredArgsConstructor;
/**
 * REQ-M 
 * 학습과정 관리
 * @author ohhoonim 
 */
@Service("learrningCourseOutlineService")
@Transactional
@RequiredArgsConstructor
public class LearningCourseOutlineService {
    private final LearningCourseOutlineCommandPort commandPort;

    public void addLearningCourseOutline(LearningCourseOutline learningCourseOutline) {
        commandPort.addLearningCourseOutline(learningCourseOutline);
    }
}
