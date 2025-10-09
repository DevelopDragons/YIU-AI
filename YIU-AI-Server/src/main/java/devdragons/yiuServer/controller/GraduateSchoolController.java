package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.GraduateSchoolRequestDto;
import devdragons.yiuServer.dto.response.GraduateSchoolResponseDto;
import devdragons.yiuServer.service.GraduateSchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/graduateSchool")
@RequiredArgsConstructor
public class GraduateSchoolController extends CommonController<GraduateSchoolResponseDto, GraduateSchoolRequestDto> {
    private final GraduateSchoolService graduateSchoolService;

    @Override
    protected boolean createEntity(GraduateSchoolRequestDto requestDto) throws Exception {
        return graduateSchoolService.createGraduateSchool(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, GraduateSchoolRequestDto requestDto) throws Exception {
        return graduateSchoolService.updateGraduateSchool(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return graduateSchoolService.deleteGraduateSchool(id);
    }

    @Override
    protected List<GraduateSchoolResponseDto> getEntities() throws Exception {
        return graduateSchoolService.getGraduateSchool();
    }
}
