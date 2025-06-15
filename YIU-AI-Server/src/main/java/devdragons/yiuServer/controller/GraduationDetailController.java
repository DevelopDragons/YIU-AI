package devdragons.yiuServer.controller;

import devdragons.yiuServer.domain.GraduationDetail;
import devdragons.yiuServer.dto.request.GraduationDetailRequestDto;
import devdragons.yiuServer.dto.response.GraduationDetailResponseDto;
import devdragons.yiuServer.service.GraduationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/graduationDetail")
@RequiredArgsConstructor
public class GraduationDetailController extends CommonController<GraduationDetailResponseDto, GraduationDetailRequestDto> {
    private final GraduationDetailService graduationDetailService;
    
    @Override
    protected boolean createEntity(GraduationDetailRequestDto requestDto) throws Exception {
        return graduationDetailService.createGraduationDetail(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, GraduationDetailRequestDto requestDto) throws Exception {
        return graduationDetailService.updateGraduationDetail(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return graduationDetailService.deleteGraduationDetail(id);
    }

    @GetMapping(params = "year")
    public ResponseEntity<List<GraduationDetail>> getGraduationDetail(@RequestParam(name = "year") Integer year) throws Exception {
        return new ResponseEntity<>(graduationDetailService.getGraduationDetailByYear(year), HttpStatus.OK);
    }

    @Override
    protected List<GraduationDetailResponseDto> getEntities() throws Exception {
        return List.of();
    }
}
