package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MicroDegreeRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeResponseDto;
import devdragons.yiuServer.service.MicroDegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/microdegree")
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
        return microDegreeService.getMd();
    }
}
