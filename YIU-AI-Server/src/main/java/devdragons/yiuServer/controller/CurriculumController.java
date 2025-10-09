package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.CurriculumRequestDto;
import devdragons.yiuServer.dto.response.CurriculumResponseDto;
import devdragons.yiuServer.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/curriculum")
@RequiredArgsConstructor
public class CurriculumController extends CommonController<CurriculumResponseDto, CurriculumRequestDto> {
    private final CurriculumService curriculumService;

    @Override
    protected boolean createEntity(CurriculumRequestDto requestDto) throws Exception {
        return curriculumService.createCurriculum(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, CurriculumRequestDto requestDto) throws Exception {
        return curriculumService.updateCurriculum(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return curriculumService.deleteCurriculum(id);
    }

    @Override
    protected List<CurriculumResponseDto> getEntities() throws Exception {
        return curriculumService.getCurriculum();
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> create(@ModelAttribute CurriculumRequestDto requestDto) throws Exception {
        return super.create(requestDto);
    }

    @PutMapping(value = "/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> update(@RequestParam("id") Integer id, @ModelAttribute CurriculumRequestDto requestDto) throws Exception {
        return super.update(id, requestDto);
    }
}
