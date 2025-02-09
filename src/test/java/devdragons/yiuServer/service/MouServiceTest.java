package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.MOU;
import devdragons.yiuServer.dto.request.MouRequestDto;
import devdragons.yiuServer.dto.response.MouResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MouRepository;
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
class MouServiceTest {
    @Autowired
    private MouService mouService;

    @Autowired
    private MouRepository mouRepository;

    @BeforeEach
    void setUp() {
        mouRepository.deleteAll();
    }

    @Test
    @DisplayName("mou 등록 성공 테스트")
    void mou등록_성공() throws Exception {
        // given
        MouRequestDto dto = new MouRequestDto();
        dto.setName("MOU");

        // when
        mouService.createMou(dto);

        // then
        Optional<MOU> mou = mouRepository.findByName("MOU");
        assertTrue(mou.isPresent());
    }

    @Test
    @DisplayName("mou 등록 실패 테스트 - 데이터 부족")
    void mou등록_실패_데이터부족() throws Exception {
        // given
        MouRequestDto dto = new MouRequestDto();
        dto.setName("");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mouService.createMou(dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("mou 수정 성공 테스트")
    void mou수정_성공() throws Exception {
        // given
        MOU mou = MOU.builder()
                .name("MOU")
                .build();

        MOU savedMOU = mouRepository.save(mou);
        MouRequestDto dto = new MouRequestDto();
        dto.setName("mou");

        // when
        mouService.updateMou(savedMOU.getId(), dto);

        // then
        Optional<MOU> mou1 = mouRepository.findById(savedMOU.getId());
        assertTrue(mou1.get().getName().equals(dto.getName()));
    }

    @Test
    @DisplayName("mou 수정 실패 케이스 - 데이터 부족")
    void mou수정_실패_데이터부족() throws Exception {
        // given
        MOU mou = MOU.builder()
                .name("MOU")
                .build();

        MOU savedMOU = mouRepository.save(mou);
        MouRequestDto dto = new MouRequestDto();
        dto.setName("");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mouService.updateMou(savedMOU.getId(), dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("mou 삭제 성공 테스트")
    void mou삭제_성공() throws Exception {
        // given
        MOU mou = MOU.builder()
                .name("MOU")
                .build();

        MOU savedMOU = mouRepository.save(mou);

        // when
        mouService.deleteMou(savedMOU.getId());

        // then
        assertTrue(mouRepository.findById(savedMOU.getId()).isEmpty());
    }

    @Test
    @DisplayName("mou 삭제 실패 테스트 - 해당 데이터 없음")
    void mou삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mouService.deleteMou(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("mou 조회 성공 테스트")
    void 학생회조회성공() throws Exception {
        // given
        MouRequestDto dto1 = new MouRequestDto();
        dto1.setName("mou");
        mouService.createMou(dto1);

        MouRequestDto dto2 = new MouRequestDto();
        dto2.setName("mou");
        mouService.createMou(dto2);

        // when
        List<MouResponseDto> mouResponseDtoList = mouService.getMou();

        assertTrue(mouResponseDtoList.size() == 2);
    }
}