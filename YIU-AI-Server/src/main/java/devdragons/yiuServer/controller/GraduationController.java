package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.GraduationRequestDto;
import devdragons.yiuServer.dto.response.GraduationResponseDto;
import devdragons.yiuServer.service.GraduationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/graduation")
@RequiredArgsConstructor
public class GraduationController extends CommonController<GraduationResponseDto, GraduationRequestDto> {
    private final GraduationService graduationService;

    @Override
    protected boolean createEntity(GraduationRequestDto requestDto) throws Exception {
        return graduationService.createGraduation(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, GraduationRequestDto requestDto) throws Exception {
        return graduationService.updateGraduation(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return graduationService.deleteGraduation(id);
    }

    @Override
    protected List<GraduationResponseDto> getEntities() throws Exception {
        return graduationService.getGraduation();
    }
}
