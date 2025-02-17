package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.InfoRequestDto;
import devdragons.yiuServer.dto.response.InfoResponseDto;
import devdragons.yiuServer.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController extends CommonController<InfoResponseDto, InfoRequestDto> {
    private final InfoService infoService;

    @Override
    protected boolean createEntity(InfoRequestDto requestDto) throws Exception {
        return infoService.createInfo(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, InfoRequestDto requestDto) throws Exception {
        return infoService.updateInfo(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return infoService.deleteInfo(id);
    }

    @Override
    protected List<InfoResponseDto> getEntities() throws Exception {
        return infoService.getInfos();
    }
}
