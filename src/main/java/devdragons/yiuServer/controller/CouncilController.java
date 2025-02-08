package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.CouncilRequestDto;
import devdragons.yiuServer.service.CouncilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/council")
@RequiredArgsConstructor
public class CouncilController {
    private final CouncilService councilService;

    // 학생회 조회
    @GetMapping
    public ResponseEntity<Object> getCouncil() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(councilService.getCouncil(), headers, HttpStatus.OK);
    }

    // 학생회 등록
    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> addCouncil(CouncilRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(councilService.createCouncil(requestDto), HttpStatus.OK);
    }

    // 학생회 수정
    @PutMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> updateCouncil(@RequestParam(name = "id") Integer id, CouncilRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(councilService.updateCouncil(id, requestDto), HttpStatus.OK);
    }

    // 학생회 삭제
    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> deleteCouncil(@RequestParam(name = "id") Integer id) throws Exception {
        return new ResponseEntity<>(councilService.deleteCouncil(id), HttpStatus.OK);
    }
}
