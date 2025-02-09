package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.InfoRequestDto;
import devdragons.yiuServer.dto.response.InfoResponseDto;
import devdragons.yiuServer.service.InfoService;
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
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    // info 등록
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> createInfo(InfoRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(infoService.createInfo(requestDto), HttpStatus.OK);
    }

    // info 수정
    @PutMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateInfo(@RequestParam(value = "id") Integer id, InfoRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(infoService.updateInfo(id, requestDto), HttpStatus.OK);
    }

    // info 삭제
    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> deleteInfo(@RequestParam(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(infoService.deleteInfo(id), HttpStatus.OK);
    }

    // info 조회
    @GetMapping
    public ResponseEntity<List<InfoResponseDto>> getInfo() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(infoService.getInfos(), headers, HttpStatus.OK);
    }
}
