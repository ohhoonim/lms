package dev.ohhoonim.lms.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.lms.component.masterCode.LectureMethod;
import dev.ohhoonim.lms.domain.learningCourses.model.Lecture;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
import dev.ohhoonim.lms.domain.learningCourses.model.SubjectService;
import dev.ohhoonim.lms.domain.learningCourses.model.Syllabus;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.InvalidParameters;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.NotFound;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @InjectMocks
    SubjectService service;

    @Mock
    SubjectCommand command;
    @Mock
    SubjectQuery query;

    @Test
    @DisplayName("과목 목록 조회, 과명명 필수 2자 이상")
    public void findSubjectListNonValid() {
        // given
        var paramsSubject = Subject.builder()
                .subjectName("수")
                .build();

        // then
        assertThrowsExactly(InvalidParameters.class, () -> service.findSubjectList(paramsSubject));
    }

    @Test
    @DisplayName("과목 목록 조회, 강의방법 정보 포함")
    public void findSubjectList() {
        // given
        List<Lecture> lectures = List.of(
                Lecture.builder().lectureMethod(LectureMethod.BOTH).build(),
                Lecture.builder().lectureMethod(LectureMethod.ON_LINE).build(),
                Lecture.builder().build());
        var syllabus = Syllabus.builder()
                .id(UUID.randomUUID())
                .syllabusTitle("수항2학년")
                .lectures(lectures)
                .build();

        var paramsSubject = Subject.builder()
                .subjectName("수학")
                .syllabus(syllabus)
                // .syllabus(null)
                .build();
        // when
        List<Subject> subjectList = List.of(paramsSubject);
        when(query.findSubjectList(any())).thenReturn(subjectList);

        // then
        List<Subject> result = service.findSubjectList(paramsSubject);
        // lectureMethods는 도메인에서 계산 처리한다.
        assertEquals(2, result.get(0).getLectureMethods().size());
        // assertNull(result.get(0).getSyllabus());
        verify(query, times(1)).findSubjectList(any());
    }

    @Test
    @DisplayName("과목 목록 조회, syllabus에 lecture가 없을때 ")
    public void findSubjectListNoSyllabus() {
        // given
        var syllabus = Syllabus.builder()
                .id(UUID.randomUUID())
                .syllabusTitle("수항2학년")
                .lectures(null)
                .build();

        var paramsSubject = Subject.builder()
                .subjectName("수학")
                .syllabus(syllabus)
                .build();
        // when
        List<Subject> subjectList = List.of(paramsSubject);
        when(query.findSubjectList(any())).thenReturn(subjectList);

        // then
        List<Subject> result = service.findSubjectList(paramsSubject);
        assertNull(result.get(0).getLectureMethods());
        assertEquals("수항2학년", result.get(0).getSyllabus().getSyllabusTitle());
        verify(query, times(1)).findSubjectList(any());
    }

    @Test
    @DisplayName("과목 저정")
    public void saveSubject() {
        service.saveSubject(Subject.builder().build());
        verify(command).saveSubject(any());
    }

    @Test
    @DisplayName("과목별 강의계획서 찾기")
    public void getSyllabusInSubject() {
        // given
        var subjectId = UUID.randomUUID();

        // when
        when(query.findSubjectById(any())).thenReturn(Optional.empty());

        // then
        assertThrowsExactly(NotFound.class, () -> 
            service.getSyllabusInSubject(subjectId) 
        );
    }
}























