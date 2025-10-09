package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MicroDegreeSubjectRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeSubjectResponseDto;
import devdragons.yiuServer.service.MicroDegreeSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/microdegreeSubject")
@RequiredArgsConstructor
public class MicroDegreeSubjectController extends CommonController<MicroDegreeSubjectResponseDto, MicroDegreeSubjectRequestDto> {
    private final MicroDegreeSubjectService microDegreeSubjectService;

    @Override
    protected boolean createEntity(MicroDegreeSubjectRequestDto requestDto) throws Exception {
        return microDegreeSubjectService.createMicroDegreeSubject(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, MicroDegreeSubjectRequestDto requestDto) throws Exception {
        return microDegreeSubjectService.updateMicroDegreeSubject(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return microDegreeSubjectService.deleteMicroDegreeSubject(id);
    }

    @Override
    protected List<MicroDegreeSubjectResponseDto> getEntities() throws Exception {
        return null;
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> create(@ModelAttribute MicroDegreeSubjectRequestDto requestDto) throws Exception {
        return super.create(requestDto);
    }

    @PutMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> update(@RequestParam("id") Integer id, @ModelAttribute MicroDegreeSubjectRequestDto requestDto) throws Exception {
        return super.update(id, requestDto);
    }
}
