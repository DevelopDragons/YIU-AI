package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Member;
import devdragons.yiuServer.domain.state.MemberRoleCategory;
import devdragons.yiuServer.domain.state.ProfessorTypeCategory;
import devdragons.yiuServer.dto.request.MemberRequestDto;
import devdragons.yiuServer.dto.response.MemberResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("member 등록 성공 테스트")
    void member등록_성공_테스트() throws Exception {
        // given
        MemberRequestDto dto = new MemberRequestDto();
        dto.setName("name");
        dto.setMail("mail");
        dto.setTel("tel");
        dto.setLabName("labName");
        dto.setLabLink("labLink");
        dto.setLabCategory("labCategory");
        dto.setType(ProfessorTypeCategory.FULL_TIME);
        dto.setRole(MemberRoleCategory.ASSISTANT);
        dto.setDescription("description");

        // when
        memberService.createMember(dto);

        // then
        Optional<Member> savedMember = memberRepository.findByName("name");
        assertTrue(memberRepository.findById(savedMember.get().getId()).isPresent());
    }

    @Test
    @DisplayName("member 등록 실패 테스트 - 데이터 부족")
    void member등록_실패_데이터부족() throws Exception {
        // given
        MemberRequestDto dto = new MemberRequestDto();
        dto.setLabLink("labLink");
        dto.setLabCategory("labCategory");
        dto.setType(ProfessorTypeCategory.FULL_TIME);
        dto.setRole(MemberRoleCategory.ASSISTANT);
        dto.setDescription("description");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> memberService.createMember(dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("member 수정 성공 테스트")
    void member수정_성공() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .mail("mail")
                .tel("tel")
                .labName("labName")
                .labLink("labLink")
                .labCategory("labCategory")
                .type(ProfessorTypeCategory.FULL_TIME)
                .role(MemberRoleCategory.ASSISTANT)
                .description("description")
                .build();
        int savedMemberId = memberRepository.save(member).getId();

        // when
        MemberRequestDto dto = new MemberRequestDto();
        dto.setName("이름");
        dto.setMail("mail");
        dto.setTel("tel");
        dto.setLabName("labName");
        dto.setLabLink("labLink");
        dto.setLabCategory("labCategory");
        dto.setType(ProfessorTypeCategory.FULL_TIME);
        dto.setRole(MemberRoleCategory.ASSISTANT);
        dto.setDescription("description");
        memberService.updateMember(savedMemberId, dto);

        // then
        assertTrue(memberRepository.findById(savedMemberId).get().getName().equals("이름"));
    }

    @Test
    @DisplayName("member 수정 실패 테스트 - 데이터 부족")
    void member수정_실패_데이터부족() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .mail("mail")
                .tel("tel")
                .labName("labName")
                .labLink("labLink")
                .labCategory("labCategory")
                .type(ProfessorTypeCategory.FULL_TIME)
                .role(MemberRoleCategory.ASSISTANT)
                .description("description")
                .build();
        int savedMemberId = memberRepository.save(member).getId();

        MemberRequestDto dto = new MemberRequestDto();
        dto.setName("");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> memberService.updateMember(savedMemberId, dto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("member 삭제 성공 테스트")
    void member삭제_성공() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .mail("mail")
                .tel("tel")
                .labName("labName")
                .labLink("labLink")
                .labCategory("labCategory")
                .type(ProfessorTypeCategory.FULL_TIME)
                .role(MemberRoleCategory.ASSISTANT)
                .description("description")
                .build();
        int savedMemberId = memberRepository.save(member).getId();

        // when
        memberService.deleteMember(savedMemberId);

        // then
        assertTrue(memberRepository.findById(savedMemberId).isEmpty());
    }

    @Test
    @DisplayName("member 삭제 실패 테스트 - 해당 데이터 없음")
    void member삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> memberService.deleteMember(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("member 조회 성공 테스트")
    void member조회성공() throws Exception {
        // given
        MemberRequestDto dto = new MemberRequestDto();
        dto.setName("name");
        dto.setMail("mail");
        dto.setTel("tel");
        dto.setLabName("labName");
        dto.setLabLink("labLink");
        dto.setLabCategory("labCategory");
        dto.setType(ProfessorTypeCategory.FULL_TIME);
        dto.setRole(MemberRoleCategory.ASSISTANT);
        dto.setDescription("description");
        memberService.createMember(dto);

        MemberRequestDto dto2 = new MemberRequestDto();
        dto2.setName("name");
        dto2.setMail("mail");
        dto2.setTel("tel");
        dto2.setLabName("labName");
        dto2.setLabLink("labLink");
        dto2.setLabCategory("labCategory");
        dto2.setType(ProfessorTypeCategory.FULL_TIME);
        dto2.setRole(MemberRoleCategory.ASSISTANT);
        dto2.setDescription("description");
        memberService.createMember(dto2);

        // when
        List<MemberResponseDto> memberList = memberService.getMember();

        // then
        assertTrue(memberList.size() == 2);
    }
}