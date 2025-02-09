package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Info;
import devdragons.yiuServer.dto.request.InfoRequestDto;
import devdragons.yiuServer.dto.response.InfoResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.InfoRepository;
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
class InfoServiceTest {
    @Autowired
    private InfoService infoService;

    @Autowired
    private InfoRepository infoRepository;

    @BeforeEach
    void setUp() {
        infoRepository.deleteAll();
    }

    @Test
    @DisplayName("info 등록 성공 테스트")
    void info등록_성공() throws Exception {
        // given
        InfoRequestDto infoRequestDto = new InfoRequestDto();
        infoRequestDto.setName("name");
        infoRequestDto.setEngName("engName");
        infoRequestDto.setAddress("address");
        infoRequestDto.setTel("tel");
        infoRequestDto.setMail("mail");
        infoRequestDto.setProfessor("professor");
        infoRequestDto.setGreeting("greeting");
        infoRequestDto.setTitle("title");
        infoRequestDto.setContents("contents");

        // when
        infoService.createInfo(infoRequestDto);

        // then
        Optional<Info> news = infoRepository.findByName("name");
        assertTrue(news.isPresent());
    }

    @Test
    @DisplayName("info 등록 실패 테스트 - 데이터 부족")
    void info등록_실패_데이터부족() throws Exception {
        // given
        InfoRequestDto infoRequestDto = new InfoRequestDto();
        infoRequestDto.setContents("contents");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> infoService.createInfo(infoRequestDto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("info 수정 성공 테스트")
    void info수정_성공() throws Exception {
        // given
        Info info = Info.builder()
                .name("name")
                .engName("engName")
                .address("address")
                .tel("tel")
                .mail("mail")
                .professor("professor")
                .greeting("greeting")
                .title("title")
                .contents("contents")
                .build();

        Info savesInfo = infoRepository.save(info);
        InfoRequestDto infoRequestDto = new InfoRequestDto();
        infoRequestDto.setName("name");
        infoRequestDto.setEngName("engName");
        infoRequestDto.setAddress("address");
        infoRequestDto.setTel("tel");
        infoRequestDto.setMail("mail");
        infoRequestDto.setProfessor("professor");
        infoRequestDto.setGreeting("greeting");
        infoRequestDto.setTitle("title");
        infoRequestDto.setContents("내용");

        // when
        infoService.updateInfo(savesInfo.getId(), infoRequestDto);

        // then
        Optional<Info> info1 = infoRepository.findById(savesInfo.getId());
        assertTrue(info1.get().getContents().equals(infoRequestDto.getContents()));
    }

    @Test
    @DisplayName("info 수정 실패 테스트 - 데이터 부족")
    void info수정_실패_데이터부족() throws Exception {
        // given
        Info info = Info.builder()
                .name("name")
                .engName("engName")
                .address("address")
                .tel("tel")
                .mail("mail")
                .professor("professor")
                .greeting("greeting")
                .title("title")
                .contents("contents")
                .build();

        Info savesInfo = infoRepository.save(info);
        InfoRequestDto infoRequestDto = new InfoRequestDto();
        infoRequestDto.setContents("내용");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> infoService.updateInfo(savesInfo.getId(), infoRequestDto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("info 삭제 성공 테스트")
    void info삭제_성공() throws Exception {
        // given
        Info info = Info.builder()
                .name("name")
                .engName("engName")
                .address("address")
                .tel("tel")
                .mail("mail")
                .professor("professor")
                .greeting("greeting")
                .title("title")
                .contents("contents")
                .build();

        Info savesInfo = infoRepository.save(info);

        // when
        infoService.deleteInfo(savesInfo.getId());

        // then
        assertTrue(infoRepository.findById(savesInfo.getId()).isEmpty());
    }

    @Test
    @DisplayName("뉴스 삭제 실패 테스트 - 해당 데이터 없음")
    void 뉴스삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> infoService.deleteInfo(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("info 조회 성공 테스트")
    void info조회성공() throws Exception {
        // given
        InfoRequestDto infoRequestDto = new InfoRequestDto();
        infoRequestDto.setName("name");
        infoRequestDto.setEngName("engName");
        infoRequestDto.setAddress("address");
        infoRequestDto.setTel("tel");
        infoRequestDto.setMail("mail");
        infoRequestDto.setProfessor("professor");
        infoRequestDto.setGreeting("greeting");
        infoRequestDto.setTitle("title");
        infoRequestDto.setContents("내용");
        infoService.createInfo(infoRequestDto);

        InfoRequestDto infoRequestDto2 = new InfoRequestDto();
        infoRequestDto2.setName("name");
        infoRequestDto2.setEngName("engName");
        infoRequestDto2.setAddress("address");
        infoRequestDto2.setTel("tel");
        infoRequestDto2.setMail("mail");
        infoRequestDto2.setProfessor("professor");
        infoRequestDto2.setGreeting("greeting");
        infoRequestDto2.setTitle("title");
        infoRequestDto2.setContents("내용");
        infoService.createInfo(infoRequestDto2);

        // when
        List<InfoResponseDto> infoResponseDtos = infoService.getInfos();

        // then
        assertTrue(infoResponseDtos.size() == 2);
    }
}