package dev.ohhoonim.lms.domain.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumService;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumQuery;

@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

    @InjectMocks
    CurriculumService service;

    @Mock
    CurriculumQuery query;

    @Mock
    CurriculumCommand command;

    @Test
    @DisplayName("신규 커리큘럼 생성")
    public void newCurriculumTest() {
        // given
        var param = Curriculum.builder()
                .curriculumName("2024 스프링과정")
                .build();

        // when
        var cRound = CurriculumRound.builder()
                .id(1L)
                .roundName("1차")
                .build();
        var storedCurriculum = Curriculum.builder()
                .id(1L)
                .curriculumName("2024 스프링과정")
                .useYn(true)
                .curriculumRound(cRound)
                .build();

        when(command.saveCurriculum(any())).thenReturn(storedCurriculum);

        // then
        Curriculum newCurriculum = service.saveCurriculum(param);

        // 입력한 커리큘럼명이 그대로 저장됨
        assertEquals("2024 스프링과정", newCurriculum.getCurriculumName());
        // 신규 커리큘럼이 생성됨
        assertEquals(1L, newCurriculum.getId());
        // 신규 커리큘럼은 디폴트로 '사용'으로 설정됨
        assertTrue(newCurriculum.getUseYn());
        // 라운드 정보를 입력하지 않았을 경우 자동으로 "1차"수 라운드가 생성됨
        assertEquals(1L, newCurriculum.getCurriculumRound().getId());
        assertEquals("1차", newCurriculum.getCurriculumRound().getRoundName());

    }

    @Test
    @DisplayName("커리큘럼 수정")
    public void updateCurriculumTest() {
        // given
        var oldcRound = CurriculumRound.builder()
                .id(1L)
                .roundName("1차-2") // 변경된 사항
                .build();
        var param = Curriculum.builder()
                .id(1L)
                .curriculumName("2024 스프링과정")
                .useYn(false) // 변경된 사항
                .curriculumRound(oldcRound)
                .build();

        // when
        var cRound = CurriculumRound.builder()
                .id(1L)
                .roundName("1차-2")
                .build();
        var storedCurriculum = Curriculum.builder()
                .id(1L)
                .curriculumName("2024 스프링과정")
                .useYn(false)
                .curriculumRound(cRound)
                .build();

        when(command.saveCurriculum(any())).thenReturn(storedCurriculum);

        // then
        Curriculum newCurriculum = service.saveCurriculum(param);

        // 사용여부를 false로 저장함
        assertFalse(newCurriculum.getUseYn());
        // 라운드명 변경됨
        assertEquals("1차-2", newCurriculum.getCurriculumRound().getRoundName());
    }

    @Test
    @DisplayName("커리큘럼 수정 - 라운드 정보를 주지 않았을 경우, 라운드가 추가되면 안됨")
    // 이건 테스트코드로 잘 표현이 안되네...
    public void updateNoRoundCurriculumTest() {
        // given
        var param = Curriculum.builder()
                .id(1L)
                .curriculumName("2024 스프링과정")
                .useYn(false) // 변경된 사항
                .build();

        // when
        var cRound = CurriculumRound.builder()
                .id(1L)
                .roundName("1차-2")
                .build();
        var storedCurriculum = Curriculum.builder()
                .id(1L)
                .curriculumName("2024 스프링과정")
                .useYn(false)
                .curriculumRound(cRound)
                .build();

        when(command.saveCurriculum(any())).thenReturn(storedCurriculum);

        // then
        Curriculum newCurriculum = service.saveCurriculum(param);

        // 사용여부를 false로 저장함
        assertFalse(newCurriculum.getUseYn());
        // 라운드 정보는 저장된 정보를 가져옴
        assertEquals("1차-2", newCurriculum.getCurriculumRound().getRoundName());
    }

    @Test
    @DisplayName("커리큘럼에 신규 차수 추가")
    public void newRoundTest() {
       // TODO  시간을 두고 천천히 진행해보기  
    }
}
