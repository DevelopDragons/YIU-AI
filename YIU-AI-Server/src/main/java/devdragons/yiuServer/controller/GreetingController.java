package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.GreetingRequestDto;
import devdragons.yiuServer.dto.response.GreetingResponseDto;
import devdragons.yiuServer.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/greeting")
@RequiredArgsConstructor
public class GreetingController extends CommonController<GreetingResponseDto, GreetingRequestDto>{
    private final GreetingService greetingService;

    @Override
    protected boolean createEntity(GreetingRequestDto requestDto) throws Exception {
        return greetingService.createGreeting(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, GreetingRequestDto requestDto) throws Exception {
        return greetingService.updateGreeting(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return greetingService.deleteGreeting(id);
    }

    @Override
    protected List<GreetingResponseDto> getEntities() throws Exception {
        return greetingService.getGreeting();
    }
}
