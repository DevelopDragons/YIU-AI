package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MemberRequestDto;
import devdragons.yiuServer.dto.response.MemberResponseDto;
import devdragons.yiuServer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // member 등록
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createMember(MemberRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(memberService.createMember(requestDto), HttpStatus.OK);
    }

    // member 수정
    @PutMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateMember(@RequestParam(value = "id") Integer id, MemberRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(memberService.updateMember(id, requestDto), HttpStatus.OK);
    }

    // member 삭제
    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> deleteMember(@RequestParam(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
    }

    // member 조회
    @GetMapping
    public ResponseEntity<Object> getMember() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(memberService.getMember(), headers, HttpStatus.OK);
    }
}
