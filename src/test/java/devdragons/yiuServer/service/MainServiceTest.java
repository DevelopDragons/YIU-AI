package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import devdragons.yiuServer.dto.LoginDto;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainServiceTest {

    @Autowired
    private MainService mainService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void 회원가입_성공() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId("202033009");
        userRequestDto.setName("김예서");
        userRequestDto.setPwd("1234");
        userRequestDto.setGrade(1);
        userRequestDto.setRole(UserRoleCategory.ADMIN);
        userRequestDto.setStatus(UserStatusCategory.STUDENT);
        userRequestDto.setMajor("33");
        userRequestDto.setDepartment("컴퓨터과학");
        userRequestDto.setTrack(UserTrackCategory.SINGLE);
        userRequestDto.setEntrance(UserEntranceCategory.FRESH);
        userRequestDto.setProfessor("이완주");

        // when
        mainService.register(userRequestDto);

        // then
        Optional<User> user = userRepository.findById(userRequestDto.getId());
        assertThat(user).isPresent();
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 데이터 미입력")
    void 회원가입_실패_데이터_미입력() {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mainService.register(userRequestDto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 데이터 중복")
    void 회원가입_실패_데이터_중복() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId("202033009");
        userRequestDto.setName("김예서");
        userRequestDto.setPwd("1234");
        userRequestDto.setGrade(1);
        userRequestDto.setRole(UserRoleCategory.ADMIN);
        userRequestDto.setStatus(UserStatusCategory.STUDENT);
        userRequestDto.setMajor("33");
        userRequestDto.setDepartment("컴퓨터과학");
        userRequestDto.setTrack(UserTrackCategory.SINGLE);
        userRequestDto.setEntrance(UserEntranceCategory.FRESH);
        userRequestDto.setProfessor("이완주");

        mainService.register(userRequestDto);

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mainService.register(userRequestDto));

        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE);
        assertThat(customException.getErrorCode().getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("회원가입 시 이메일 전송 테스트")
    void 회원가입_시_이메일_전송_테스트() throws Exception {
        // given
        String id = "202033009";

        // when
        int authNum = mainService.sendEmailWhenRegister(id);

        // then
        assertThat(authNum).isGreaterThanOrEqualTo(100000).isLessThanOrEqualTo(999999);
    }

    @Test
    @DisplayName("비밀번호 변경 시 이메일 전송 테스트")
    void 비밀번호_변경_시_이메일_전송_테스트() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId("202033009");
        userRequestDto.setName("김예서");
        userRequestDto.setPwd("1234");
        userRequestDto.setGrade(1);
        userRequestDto.setRole(UserRoleCategory.ADMIN);
        userRequestDto.setStatus(UserStatusCategory.STUDENT);
        userRequestDto.setMajor("33");
        userRequestDto.setDepartment("컴퓨터과학");
        userRequestDto.setTrack(UserTrackCategory.SINGLE);
        userRequestDto.setEntrance(UserEntranceCategory.FRESH);
        userRequestDto.setProfessor("이완주");

        mainService.register(userRequestDto);

        // when
        int authNum = mainService.sendEmailWhenPwdChange(userRequestDto.getId());

        // then
        assertThat(authNum).isGreaterThanOrEqualTo(100000).isLessThanOrEqualTo(999999);
    }

    @Test
    @DisplayName("비밀번호 변경 시 회원 미존재 오류")
    void 비밀번호_변경_시_회원_미존재_오류() {
        // given
        String id = "202033009";

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> mainService.sendEmailWhenPwdChange(id));

        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.NOT_EXIST_ID);
        assertThat(customException.getErrorCode().getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공_테스트() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId("202033009");
        userRequestDto.setName("김예서");
        userRequestDto.setPwd("1234");
        userRequestDto.setGrade(1);
        userRequestDto.setRole(UserRoleCategory.ADMIN);
        userRequestDto.setStatus(UserStatusCategory.STUDENT);
        userRequestDto.setMajor("33");
        userRequestDto.setDepartment("컴퓨터과학");
        userRequestDto.setTrack(UserTrackCategory.SINGLE);
        userRequestDto.setEntrance(UserEntranceCategory.FRESH);
        userRequestDto.setProfessor("이완주");

        mainService.register(userRequestDto);

        // when
        LoginDto loginDto = mainService.login(userRequestDto);

        // then
        LoginDto expectedLoginDto = LoginDto.builder()
                .id("202033009")
                .name("김예서")
                .grade(1)
                .role(UserRoleCategory.ADMIN)
                .status(UserStatusCategory.STUDENT)
                .major("33")
                .department("컴퓨터과학")
                .track(UserTrackCategory.SINGLE)
                .entrance(UserEntranceCategory.FRESH)
                .token(loginDto.getToken())  // 토큰 값은 직접 비교하지 않고 그대로 사용
                .build();

        assertThat(loginDto).isEqualTo(expectedLoginDto);
    }
}
