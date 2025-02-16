package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import devdragons.yiuServer.domain.state.RequiredCategory;
import devdragons.yiuServer.dto.request.MicroDegreeRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MicroDegreeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicroDegreeServiceTest {
    @Autowired
    private MicroDegreeService microDegreeService;

    @Autowired
    private MicroDegreeRepository microDegreeRepository;

    @BeforeEach
    void setUp() {
        microDegreeRepository.deleteAll();
    }

    @Test
    @DisplayName("md 등록 성공 테스트")
    void md등록_성공() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");
        md.setRequired(RequiredCategory.REQUIRED);
        md.setCategory(MicroDegreeCategory.MD);

        // when
        int savedMd = microDegreeService.createMd(md);

        // then
        assertTrue(microDegreeRepository.findById(savedMd).isPresent());
    }

    @Test
    @DisplayName("md 등록 실패 테스트 - 데이터 부족")
    void md등록_실패_데이터부족() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> microDegreeService.createMd(md));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("md 수정 성공 테스트")
    void md수정_성공() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");
        md.setRequired(RequiredCategory.REQUIRED);
        md.setCategory(MicroDegreeCategory.MD);
        int id = microDegreeService.createMd(md);

        // when
        md.setName("newName");
        microDegreeService.updateMd(id, md);

        // then
        assertTrue(microDegreeRepository.findById(id).get().getName().equals(md.getName()));
    }

    @Test
    @DisplayName("md 수정 실패 테스트 - 데이터 부족")
    void md수정_실패_데이터부족() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");
        md.setRequired(RequiredCategory.REQUIRED);
        md.setCategory(MicroDegreeCategory.MD);
        int id = microDegreeService.createMd(md);
        md.setName("");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> microDegreeService.updateMd(id, md));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("md삭제 성공 테스트")
    void md삭제_성공() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");
        md.setRequired(RequiredCategory.REQUIRED);
        md.setCategory(MicroDegreeCategory.MD);
        int id = microDegreeService.createMd(md);

        // when
        microDegreeService.deleteMd(id);

        // then
        assertTrue(microDegreeRepository.findById(id).isEmpty());
    }

    @Test
    @DisplayName("md 삭제 실패 - 해당 데이터 없음")
    void md삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> microDegreeService.deleteMd(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("md 조회 성공 테스트")
    void md조회성공() throws Exception {
        // given
        MicroDegreeRequestDto md = new MicroDegreeRequestDto();
        md.setName("name");
        md.setTitle("title");
        md.setDescription("description");
        md.setRequired(RequiredCategory.REQUIRED);
        md.setCategory(MicroDegreeCategory.MD);
        microDegreeService.createMd(md);

        MicroDegreeRequestDto md2 = new MicroDegreeRequestDto();
        md2.setName("name");
        md2.setTitle("title");
        md2.setDescription("description");
        md2.setRequired(RequiredCategory.REQUIRED);
        md2.setCategory(MicroDegreeCategory.MD);
        microDegreeService.createMd(md2);

        // when
        List<MicroDegreeResponseDto> dtos = microDegreeService.getMd();

        // then
        assertTrue(dtos.size() == 2);
    }
}