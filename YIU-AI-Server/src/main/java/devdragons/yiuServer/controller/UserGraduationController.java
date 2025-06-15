package devdragons.yiuServer.controller;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.UserGraduation;
import devdragons.yiuServer.dto.request.UserGraduationRequestDto;
import devdragons.yiuServer.dto.response.UserGraduationResponseDto;
import devdragons.yiuServer.security.CustomUserDetails;
import devdragons.yiuServer.service.UserGraduationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/userGraduation")
@RequiredArgsConstructor
public class UserGraduationController {
    private final UserGraduationService userGraduationService;

    @GetMapping
    public ResponseEntity<List<UserGraduationResponseDto>> getUserGraduation(@RequestParam(name = "id") User userId) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userGraduationService.getUserGraduation(userId), headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> submitGraduation(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(name = "id")Integer id, UserGraduationRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(userGraduationService.submitGraduation(userDetails, id, requestDto), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<Boolean> passGraduation(@RequestParam(name = "id")Integer id, UserGraduationRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(userGraduationService.passGraduation(id, requestDto), HttpStatus.OK);
    }
}
