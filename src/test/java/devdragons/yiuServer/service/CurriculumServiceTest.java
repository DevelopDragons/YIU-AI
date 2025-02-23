package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Curriculum;
import devdragons.yiuServer.domain.state.ClassCategory;
import devdragons.yiuServer.domain.state.CourseCategory;
import devdragons.yiuServer.dto.request.CurriculumRequestDto;
import devdragons.yiuServer.dto.response.CurriculumResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CurriculumRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurriculumServiceTest {
    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @BeforeEach
    void setUp() {
        curriculumRepository.deleteAll();
    }

    @Test
    @DisplayName("커리큘럼 등록 성공")
    void 커리큘럼등록_성공() throws Exception {
        // given
        CurriculumRequestDto dto = new CurriculumRequestDto();
        dto.setTitle("title");
        dto.setCourse(CourseCategory.BASIC);
        dto.setGrade(1);
        dto.setTerm(1);
        dto.setCredit(1);
        dto.setClasses(ClassCategory.PRACTICE);
        dto.setDescription("description");

        // when
        curriculumService.createCurriculum(dto);

        // then
        Optional<Curriculum> savedCurriculum = curriculumRepository.findByTitle("title");
        assertTrue(savedCurriculum.isPresent());
    }

    @Test
    @DisplayName("커리큘럼 등록 실패 - 데이터 부족")
    void 커리큘럼등록_실패_데이터부족() throws Exception {
        // given
        CurriculumRequestDto dto = new CurriculumRequestDto();
        dto.setTitle("title");
        dto.setGrade(1);
        dto.setTerm(1);
        dto.setCredit(1);
        dto.setClasses(ClassCategory.PRACTICE);
        dto.setDescription("description");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> curriculumService.createCurriculum(dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("커리큘럼 수정 성공")
    void 커리큘럼수정_성공() throws Exception {
        // given
        Curriculum curriculum = Curriculum.builder()
                .title("title")
                .course(CourseCategory.BASIC)
                .grade(1)
                .term(1)
                .credit(1)
                .classes(ClassCategory.PRACTICE)
                .description("description")
                .build();

        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        CurriculumRequestDto dto = new CurriculumRequestDto();
        dto.setTitle("제목");
        dto.setCourse(CourseCategory.BASIC);
        dto.setGrade(1);
        dto.setTerm(1);
        dto.setCredit(1);
        dto.setClasses(ClassCategory.PRACTICE);
        dto.setDescription("description");

        int savedCurriculumId = savedCurriculum.getId();
        System.out.println("savedCurriculumId = " + savedCurriculumId);

        // when
        curriculumService.updateCurriculum(savedCurriculumId, dto);

        // then
        Optional<Curriculum> curriculum1 = curriculumRepository.findById(savedCurriculumId);
        assertTrue(curriculum1.get().getTitle().equals(dto.getTitle()));
    }

    @Test
    @DisplayName("커리큘럼 수정 실패 - 데이터 부족")
    void 커리큘럼수정_실패_데이터부족() throws Exception {
        // given
        Curriculum curriculum = Curriculum.builder()
                .title("title")
                .course(CourseCategory.BASIC)
                .grade(1)
                .term(1)
                .credit(1)
                .classes(ClassCategory.PRACTICE)
                .description("description")
                .build();

        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        CurriculumRequestDto dto = new CurriculumRequestDto();
        dto.setTitle("");

        int savedCurriculumId = savedCurriculum.getId();

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> curriculumService.updateCurriculum(savedCurriculumId, dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("커리큘럼 삭제 성공 테스트")
    void 학생회삭제_성공() throws Exception {
        // given
        Curriculum curriculum = Curriculum.builder()
                .title("title")
                .course(CourseCategory.BASIC)
                .grade(1)
                .term(1)
                .credit(1)
                .classes(ClassCategory.PRACTICE)
                .description("description")
                .build();

        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        int savedCurriculumId = savedCurriculum.getId();

        // when
        curriculumService.deleteCurriculum(savedCurriculumId);

        // then
        assertTrue(curriculumRepository.findById(savedCurriculumId).isEmpty());
    }

    @Test
    @DisplayName("커리큘럼 삭제 실패 테스트 - 해당 데이터 없음")
    void 커리큘럼삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> curriculumService.deleteCurriculum(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("커리큘럼 조회 성공 테스트")
    void 커리큘럼조회성공() throws Exception {
        // given
        CurriculumRequestDto dto = new CurriculumRequestDto();
        dto.setTitle("title");
        dto.setCourse(CourseCategory.BASIC);
        dto.setGrade(1);
        dto.setTerm(1);
        dto.setCredit(1);
        dto.setClasses(ClassCategory.PRACTICE);
        dto.setDescription("description");
        curriculumService.createCurriculum(dto);

        CurriculumRequestDto dto2 = new CurriculumRequestDto();
        dto2.setTitle("title");
        dto2.setCourse(CourseCategory.BASIC);
        dto2.setGrade(1);
        dto2.setTerm(1);
        dto2.setCredit(1);
        dto2.setClasses(ClassCategory.PRACTICE);
        dto2.setDescription("description");
        curriculumService.createCurriculum(dto2);

        // when
        List<CurriculumResponseDto> curriculumList = curriculumService.getCurriculum();

        // then
        assertTrue(curriculumList.size() == 2);
    }
}