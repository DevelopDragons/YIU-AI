package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Council;
import devdragons.yiuServer.dto.request.CouncilRequestDto;
import devdragons.yiuServer.dto.response.CouncilResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CouncilRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouncilServiceTest {
    @Autowired
    private CouncilService councilService;

    @Autowired
    private CouncilRepository councilRepository;

    @BeforeEach
    void setUp() {
        councilRepository.deleteAll();
    }

    @Test
    @DisplayName("학생회 등록 성공 테스트")
    void 학생회등록_성공() throws Exception {
        // given
        CouncilRequestDto dto = new CouncilRequestDto();
        dto.setName("AI 학생회");
        dto.setLink("link");
        dto.setYear(2025);
        dto.setSlogan("slogan");
        dto.setDescription("description");

        // when
        councilService.createCouncil(dto);

        // then
        Optional<Council> council = councilRepository.findByName("AI 학생회");
        assertTrue(council.isPresent());
    }

    @Test
    @DisplayName("학생회 등록 실패 테스트 - 데이터 부족")
    void 학생회등록_실패_데이터부족() throws Exception {
        // given
        CouncilRequestDto dto = new CouncilRequestDto();
        dto.setLink("link");
        dto.setYear(2025);
        dto.setSlogan("slogan");
        dto.setDescription("description");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> councilService.createCouncil(dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("학생회 수정 성공 테스트")
    void 학생회수정_성공() throws Exception {
        // given
        Council council = Council.builder()
                .name("AI 학생회")
                .link("link")
                .year(2025)
                .slogan("slogan")
                .description("description")
                .build();

        Council savedCouncil = councilRepository.save(council);
        CouncilRequestDto dto = new CouncilRequestDto();
        dto.setName("컴퓨터과학과 학생회");
        dto.setLink("link");
        dto.setYear(2025);
        dto.setSlogan("slogan");
        dto.setDescription("description");

        int savedCouncilId = savedCouncil.getId();

        // when
        councilService.updateCouncil(savedCouncilId, dto);

        // then
        Optional<Council> council1 = councilRepository.findById(savedCouncilId);
        assertTrue(council1.get().getName().equals(dto.getName()));
    }

    @Test
    @DisplayName("학생회 수정 실패 케이스 - 데이터 부족")
    void 학생회수정_실패_데이터부족() throws Exception {
        // given
        Council council = Council.builder()
                .name("AI 학생회")
                .link("link")
                .year(2025)
                .slogan("slogan")
                .description("description")
                .build();

        Council savedCouncil = councilRepository.save(council);
        CouncilRequestDto dto = new CouncilRequestDto();
        dto.setLink("link");
        dto.setYear(2025);
        dto.setSlogan("slogan");
        dto.setDescription("description");

        int savedCouncilId = savedCouncil.getId();

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> councilService.updateCouncil(savedCouncilId, dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("학생회 삭제 성공 테스트")
    void 학생회삭제_성공() throws Exception {
        // given
        Council council = Council.builder()
                .name("AI 학생회")
                .link("link")
                .year(2025)
                .slogan("slogan")
                .description("description")
                .build();

        Council savedCouncil = councilRepository.save(council);
        int savedCouncilId = savedCouncil.getId();

        // when
        councilService.deleteCouncil(savedCouncilId);

        // then
        assertTrue(councilRepository.findById(savedCouncilId).isEmpty());
    }

    @Test
    @DisplayName("학생회 삭제 실패 테스트 - 해당 데이터 없음")
    void 학생회삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> councilService.deleteCouncil(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("학생회 조회 성공 테스트")
    void 학생회조회성공() throws Exception {
        // given
        CouncilRequestDto dto1 = new CouncilRequestDto();
        dto1.setName("AI 학생회");
        dto1.setLink("link");
        dto1.setYear(2025);
        dto1.setSlogan("slogan");
        dto1.setDescription("description");
        councilService.createCouncil(dto1);

        CouncilRequestDto dto2 = new CouncilRequestDto();
        dto2.setName("컴퓨터과학과 학생회");
        dto2.setLink("link");
        dto2.setYear(2024);
        dto2.setSlogan("slogan");
        dto2.setDescription("description");
        councilService.createCouncil(dto2);

        // when
        List<CouncilResponseDto> councilList = councilService.getCouncil();

        assertTrue(councilList.size() == 2);
    }
}