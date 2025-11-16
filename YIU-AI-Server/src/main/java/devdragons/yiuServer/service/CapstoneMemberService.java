package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.CapstoneMember;
import devdragons.yiuServer.repository.CapstoneMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CapstoneMemberService {
    private final CapstoneMemberRepository capstoneMemberRepository;

    /*
     * @description Capstone 멤버 등록
     * @author 김예서
     * @param capstoneId, captain, member1~9
     * @return Boolean
     * */
}
