package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MicroDegreeRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeResponseDto;
import devdragons.yiuServer.service.MicroDegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/microdegree")
@RequiredArgsConstructor
public class MicroDegreeController extends CommonController<MicroDegreeResponseDto, MicroDegreeRequestDto> {
    private final MicroDegreeService microDegreeService;

    @Override
    protected boolean createEntity(MicroDegreeRequestDto requestDto) throws Exception {
        return microDegreeService.createMd(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, MicroDegreeRequestDto requestDto) throws Exception {
        return microDegreeService.updateMd(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return microDegreeService.deleteMd(id);
    }

    @Override
    protected List<MicroDegreeResponseDto> getEntities() throws Exception {
        return microDegreeService.getMdList();
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> create(@ModelAttribute MicroDegreeRequestDto requestDto) throws Exception {
        return super.create(requestDto);
    }

    @PutMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> update(@RequestParam("id") Integer id, @ModelAttribute MicroDegreeRequestDto requestDto) throws Exception {
        return super.update(id, requestDto);
    }
}
