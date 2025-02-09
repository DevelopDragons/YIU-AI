package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MouRequestDto;
import devdragons.yiuServer.dto.response.MouResponseDto;
import devdragons.yiuServer.service.MouService;
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
@RequestMapping("/mou")
@RequiredArgsConstructor
public class MouController {
    private final MouService mouService;

    // mou 등록
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> createMou(MouRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(mouService.createMou(requestDto), HttpStatus.OK);
    }

    // mou 수정
    @PutMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateMou(@RequestParam(value = "id") Integer id, MouRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(mouService.updateMou(id, requestDto), HttpStatus.OK);
    }

    // mou 삭제
    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> deleteMou(@RequestParam(value = "id")Integer id) throws Exception {
        return new ResponseEntity<>(mouService.deleteMou(id), HttpStatus.OK);
    }

    // mou 조회
    @GetMapping
    public ResponseEntity<List<MouResponseDto>> getAllMou() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(mouService.getMou(), headers, HttpStatus.OK);
    }
}
