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
    public ResponseEntity<Boolean> join(UserRequestDto request) throws Exception {
        return new ResponseEntity<Boolean>(mainService.register(request), HttpStatus.OK);
    }
}
