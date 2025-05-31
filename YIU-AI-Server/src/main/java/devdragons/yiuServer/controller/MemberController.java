package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.MemberRequestDto;
import devdragons.yiuServer.dto.response.MemberResponseDto;
import devdragons.yiuServer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController extends CommonController<MemberResponseDto, MemberRequestDto> {
    private final MemberService memberService;

    @Override
    protected boolean createEntity(MemberRequestDto requestDto) throws Exception {
        return memberService.createMember(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, MemberRequestDto requestDto) throws Exception {
        return memberService.updateMember(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return memberService.deleteMember(id);
    }

    @Override
    protected List<MemberResponseDto> getEntities() throws Exception {
        return memberService.getMember();
    }
}
