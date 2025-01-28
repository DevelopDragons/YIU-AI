package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MainServiceTest {
    @Autowired
    MainService mainService;

    @Autowired
    UserRepository userRepository;

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
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 데이터 미입력")
    void 회원가입_실패_데이터_미입력() throws Exception {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> {mainService.register(userRequestDto);});
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 데이터 중복")
    void 회원가입_실패_데이터_중복() throws Exception {
        // given
        UserRequestDto userRequestDto1 = new UserRequestDto();
        userRequestDto1.setId("202033009");
        userRequestDto1.setName("김예서");
        userRequestDto1.setPwd("1234");
        userRequestDto1.setGrade(1);
        userRequestDto1.setRole(UserRoleCategory.ADMIN);
        userRequestDto1.setStatus(UserStatusCategory.STUDENT);
        userRequestDto1.setMajor("33");
        userRequestDto1.setDepartment("컴퓨터과학");
        userRequestDto1.setTrack(UserTrackCategory.SINGLE);
        userRequestDto1.setEntrance(UserEntranceCategory.FRESH);
        userRequestDto1.setProfessor("이완주");
        mainService.register(userRequestDto1);

        // when
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

        // then
        CustomException customException = assertThrows(CustomException.class, () -> {mainService.register(userRequestDto);});
        assertEquals(ErrorCode.DUPLICATE, customException.getErrorCode());
        assertEquals(409, customException.getErrorCode().getStatus());
    }
}