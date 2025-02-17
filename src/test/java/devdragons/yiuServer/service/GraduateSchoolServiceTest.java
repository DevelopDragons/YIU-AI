package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.GraduateSchool;
import devdragons.yiuServer.dto.request.GraduateSchoolRequestDto;
import devdragons.yiuServer.dto.response.GraduateSchoolResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.GraduateSchoolRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GraduateSchoolServiceTest {
    @Autowired
    private GraduateSchoolService graduateSchoolService;

    @Autowired
    private GraduateSchoolRepository graduateSchoolRepository;

    @BeforeEach
    void setUp() {
        graduateSchoolRepository.deleteAll();
    }

    @Test
    @DisplayName("대학원 등록 성공 테스트")
    void 대학원등록_성공() throws Exception {
        // given
        GraduateSchoolRequestDto graduateSchool = new GraduateSchoolRequestDto();
        graduateSchool.setName("name");
        graduateSchool.setSlogan("slogan");

        // when
        graduateSchoolService.createGraduateSchool(graduateSchool);

        // then
        Optional<GraduateSchool> savedGraduateSchool = graduateSchoolRepository.findByName(graduateSchool.getName());
        assertTrue(graduateSchoolRepository.findById(savedGraduateSchool.get().getId()).isPresent());
    }

    @Test
    @DisplayName("대학원 등록 실패 테스트 - 데이터 부족")
    void 대학원등록_실패_데이터부족() throws Exception {
        // given
        GraduateSchoolRequestDto graduateSchool = new GraduateSchoolRequestDto();
        graduateSchool.setName("name");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> graduateSchoolService.createGraduateSchool(graduateSchool));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("대학원 수정 성공 테스트")
    void 대학원수정_성공() throws Exception {
        // given
        GraduateSchool graduateSchool = GraduateSchool.builder()
                .name("name")
                .slogan("slogan")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        int savedGraduateSchool = graduateSchoolRepository.save(graduateSchool).getId();

        GraduateSchoolRequestDto dto = new GraduateSchoolRequestDto();
        dto.setName("newName");
        dto.setSlogan("newSlogan");

        // when
        graduateSchoolService.updateGraduateSchool(graduateSchool.getId(), dto);

        // then
        assertTrue(graduateSchoolRepository.findById(savedGraduateSchool).get().getName().equals("newName"));
    }

    @Test
    @DisplayName("대학원 수정 실패 테스트 - 데이터 부족")
    void 대학원수정_실패_데이터부족() throws Exception {
        // given
        GraduateSchool graduateSchool = GraduateSchool.builder()
                .name("name")
                .slogan("slogan")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        int savedGraduateSchool = graduateSchoolRepository.save(graduateSchool).getId();

        GraduateSchoolRequestDto dto = new GraduateSchoolRequestDto();
        dto.setName("");
        dto.setSlogan("newSlogan");
        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> graduateSchoolService.updateGraduateSchool(savedGraduateSchool, dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("대학원 삭제 성공 테스트")
    void 대학원삭제_성공() throws Exception {
        // given
        GraduateSchool graduateSchool = GraduateSchool.builder()
                .name("name")
                .slogan("slogan")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        int savedGraduateSchool = graduateSchoolRepository.save(graduateSchool).getId();

        // when
        graduateSchoolService.deleteGraduateSchool(savedGraduateSchool);

        // then
        assertTrue(graduateSchoolRepository.findById(savedGraduateSchool).isEmpty());
    }

    @Test
    @DisplayName("대학원 삭제 실패 테스트 - 해당 데이터 없음")
    void 대학원삭제_실패_해당id미존재() throws Exception {
        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> graduateSchoolService.deleteGraduateSchool(0));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("대학원 조회 성공 테스트")
    void 대학원_조회_성공() throws Exception {
        // given
        GraduateSchoolRequestDto graduateSchool = new GraduateSchoolRequestDto();
        graduateSchool.setName("name");
        graduateSchool.setSlogan("slogan");
        graduateSchoolService.createGraduateSchool(graduateSchool);

        GraduateSchoolRequestDto graduateSchool2 = new GraduateSchoolRequestDto();
        graduateSchool2.setName("name");
        graduateSchool2.setSlogan("slogan");
        graduateSchoolService.createGraduateSchool(graduateSchool2);

        // when
        List<GraduateSchoolResponseDto> dtos = graduateSchoolService.getGraduateSchool();

        // then
        assertTrue(dtos.size() == 2);
    }
}