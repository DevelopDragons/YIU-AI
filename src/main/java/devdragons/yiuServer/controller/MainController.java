package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.LoginDto;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    // 회원가입
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> register(UserRequestDto request) throws Exception {
        return new ResponseEntity<Boolean>(mainService.register(request), HttpStatus.OK);
    }

    // 회원가입 시 이메일 인증
    @PostMapping(value = "/register/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> sendEmailWhenRegister(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.sendEmailWhenRegister(request.getId()), HttpStatus.OK);
    }

    // 비밀번호 변경 시 이메일 인증
    @PostMapping(value = "/pwd/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> sendEmailWhenPwdChange(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.sendEmailWhenPwdChange(request.getId()), HttpStatus.OK);
    }

    // 비밀번호 변경
    @PostMapping(value = "/pwd/change", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> changePwd(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.changePwd(request), HttpStatus.OK);
    }

    // 로그인
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoginDto> login(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.login(request), HttpStatus.OK);
    }
}
