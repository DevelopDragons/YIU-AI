package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.CapstoneRequestDto;
import devdragons.yiuServer.dto.response.CapstoneResponseDto;
import devdragons.yiuServer.service.CapstoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/capstone")
@RequiredArgsConstructor
public class CapstoneController extends CommonController<CapstoneResponseDto, CapstoneRequestDto> {
    private final CapstoneService capstoneService;

    @Override
    protected boolean createEntity(CapstoneRequestDto requestDto) throws Exception {
        return capstoneService.createCapstone(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, CapstoneRequestDto requestDto) throws Exception {
        return false;
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return false;
    }

    @Override
    protected List<CapstoneResponseDto> getEntities() throws Exception {
        return List.of();
    }
}
