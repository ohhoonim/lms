package dev.ohhoonim.lms.domain.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import dev.ohhoonim.lms.domain.learningCourses.model.Curriculum;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumRound;
import dev.ohhoonim.lms.domain.learningCourses.model.CurriculumService;
import dev.ohhoonim.lms.domain.learningCourses.model.Subject;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.AlreadyExists;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.NotExist;
import dev.ohhoonim.lms.domain.learningCourses.model.exception.NotFound;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumCommand;
import dev.ohhoonim.lms.domain.learningCourses.model.port.CurriculumQuery;
import dev.ohhoonim.lms.domain.learningCourses.model.port.SubjectQuery;

@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

	@InjectMocks
	CurriculumService service;

	@Mock
	CurriculumQuery query;

	@Mock
	CurriculumCommand command;

	@Mock
	SubjectQuery subjectQuery;

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
	@DisplayName("특정 커리큘럼 조회")
	public void findCurriculumTest() {
		when(query.findCurriculum(any())).thenReturn(Optional.empty());

		// 없으면 Exception
		assertThrowsExactly(NotFound.class,
				() -> service.findCurriculum(1L));
	}

	@Test
	@DisplayName("커리큘럼에 신규 차수 추가")
	public void newRoundTest() {
		// given
		Long curriculumId = 1L;

		// when
		// null 로 입력한 부분은 기존 데이터를 그대로 들고 오므로 의미가 없어서...
		var prevRound = CurriculumRound.builder().round(1).build();
		var prevCurriculum = Curriculum.builder()
				.id(1L)
				.curriculumName("2024 스프링부트 입문")
				.curriculumRound(prevRound)
				.manager(null)
				.learningTarget(null)
				.useYn(true)
				.subjects(List.of(Subject.builder().build(), Subject.builder().build()))
				.build();

		when(query.findCurriculum(any())).thenReturn(Optional.of(prevCurriculum));

		// 차수가 1증가 함
		// 과목이 그대로 보전됨
		var newRound = Curriculum.builder()
				.curriculumRound(CurriculumRound.builder().round(2).build())
				.subjects(List.of(Subject.builder().build(), Subject.builder().build()))
				.build();
		when(command.saveCurriculum(any())).thenReturn(newRound);

		// then
		Curriculum result = service.newRound(curriculumId);
		assertEquals(2, result.getCurriculumRound().getRound());
		assertEquals(2, result.getSubjects().size());
	}

	@Test
	@DisplayName("과목 검색, 커리큘럼 아이디가 없을때")
	public void findSubjectNonCurriculumId() {
		// given
		Long curriculumId = 0L;
		var paramSubject = Subject.builder().build();

		// then
		assertThrowsExactly(NotFound.class, () -> service.findSubject(paramSubject, curriculumId));
	}

	@Test
	@DisplayName("과목 검색")
	public void findSubjects() {
		// given
		Long curriculumId = 1L;
		var paramSubject = Subject.builder().build();

		service.findSubject(paramSubject, curriculumId);
		verify(query, times(1)).findSubjectsInCurriculum(any(), any());

	}

	@Test
	@DisplayName("커리큘럼에 과목 등록하기, 없는 과목 ")
	public void addSubjectInCurriculumNotExist() {
		UUID checkId = UUID.randomUUID();
		// given
		var subjectParam =  checkId; 
		Long idParam = 1L;

		// when
		when(subjectQuery.findSubjectById(any()))
			.thenReturn(Optional.empty());

		// then
		assertThrowsExactly(NotExist.class, () -> 
			service.addSubjectInCurriculum(subjectParam, idParam));
	}

	@Test
	@DisplayName("커리큘럼에 과목 등록하기. 이미등록된 과목")
	public void addSubjectInCurriculumAlreadyExists() {
		UUID checkId = UUID.randomUUID();
		// given
		var subjectParam =  checkId; 
		Long idParam = 1L;

		// when
		List<Subject> resultSubject = List.of(
			Subject.builder().id(UUID.randomUUID()).build(),
			Subject.builder().id(checkId).build(),
			Subject.builder().id(UUID.randomUUID()).build()
		);
		when(query.findSubjectsInCurriculum(any(), any()))
			.thenReturn(resultSubject);

		when(subjectQuery.findSubjectById(any()))
			.thenReturn(Optional.of(Subject.builder().build()));
		// then
		assertThrowsExactly(AlreadyExists.class, () -> 
			service.addSubjectInCurriculum(subjectParam, idParam));
	}
}
