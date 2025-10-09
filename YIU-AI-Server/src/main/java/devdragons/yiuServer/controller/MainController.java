package devdragons.yiuServer.controller;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.dto.LoginDto;
import devdragons.yiuServer.dto.TokenDto;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.dto.response.PageResponseDto;
import devdragons.yiuServer.dto.response.UserResponseDto;
import devdragons.yiuServer.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    // 회원가입
    @PostMapping(value = "/api/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> register(UserRequestDto request) throws Exception {
        return new ResponseEntity<Boolean>(mainService.register(request), HttpStatus.OK);
    }

    // 회원가입 시 이메일 인증
    @PostMapping(value = "/api/register/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> sendEmailWhenRegister(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.sendEmailWhenRegister(request.getId()), HttpStatus.OK);
    }

    // 비밀번호 변경 시 이메일 인증
    @PostMapping(value = "/api/pwd/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> sendEmailWhenPwdChange(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.sendEmailWhenPwdChange(request.getId()), HttpStatus.OK);
    }

    // 비밀번호 변경
    @PostMapping(value = "/api/pwd/change", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> changePwd(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.changePwd(request), HttpStatus.OK);
    }

    // 로그인
    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoginDto> login(UserRequestDto request) throws Exception {
        return new ResponseEntity<>(mainService.login(request), HttpStatus.OK);
    }

    // 학생 조회(전체)
    @GetMapping("/api/student/all")
    public ResponseEntity<List<UserResponseDto>> getStudents() throws Exception {
        return new ResponseEntity<>(mainService.getStudents(), HttpStatus.OK);
    }

    // 학생 조회(학년별)
    @GetMapping("/api/student")
    public ResponseEntity<List<UserResponseDto>> getStudentsByGrade(@RequestParam("grade") int grade) throws Exception {
        return new ResponseEntity<>(mainService.getStudentsByGrade(grade), HttpStatus.OK);
    }

    // 학생 검색
    @GetMapping("/api/student/search")
    public ResponseEntity<PageResponseDto<UserResponseDto>> searchStudentByName(@RequestParam("name") String name, Pageable pageable) throws Exception {
        Page<UserResponseDto> usersPage = mainService.searchStudentsByName(name, pageable);
        return ResponseEntity.ok(new PageResponseDto<>(usersPage));
    }

    // accessToken 재발급
    @PostMapping(value = "/api/token/refresh", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenDto> getTokens(TokenDto request) throws Exception {
        return new ResponseEntity<>(mainService.getNewTokens(request), HttpStatus.OK);
    }
}
