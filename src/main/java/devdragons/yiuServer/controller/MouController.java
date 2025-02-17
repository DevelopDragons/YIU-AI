package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MouRequestDto;
import devdragons.yiuServer.dto.response.MouResponseDto;
import devdragons.yiuServer.service.MouService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mou")
@RequiredArgsConstructor
public class MouController extends CommonController<MouResponseDto, MouRequestDto> {
    private final MouService mouService;

    @Override
    protected boolean createEntity(MouRequestDto requestDto) throws Exception {
        return mouService.createMou(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, MouRequestDto requestDto) throws Exception {
        return mouService.updateMou(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return mouService.deleteMou(id);
    }

    @Override
    protected List<MouResponseDto> getEntities() throws Exception {
        return mouService.getMou();
    }
}
