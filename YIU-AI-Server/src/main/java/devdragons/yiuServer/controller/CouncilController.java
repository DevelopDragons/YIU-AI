package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.CouncilRequestDto;
import devdragons.yiuServer.dto.response.CouncilResponseDto;
import devdragons.yiuServer.service.CouncilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/council")
@RequiredArgsConstructor
public class CouncilController extends CommonController<CouncilResponseDto, CouncilRequestDto> {
    private final CouncilService councilService;

    @Override
    protected boolean createEntity(CouncilRequestDto requestDto) throws Exception {
        return councilService.createCouncil(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, CouncilRequestDto requestDto) throws Exception {
        return councilService.updateCouncil(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return councilService.deleteCouncil(id);
    }

    @Override
    protected List<CouncilResponseDto> getEntities() throws Exception {
        return councilService.getCouncil();
    }
}
