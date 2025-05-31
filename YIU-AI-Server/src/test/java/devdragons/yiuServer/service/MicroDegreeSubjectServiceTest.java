package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.MicroDegreeSubject;
import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import devdragons.yiuServer.dto.request.MicroDegreeSubjectRequestDto;
import devdragons.yiuServer.repository.MicroDegreeRepository;
import devdragons.yiuServer.repository.MicroDegreeSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class MicroDegreeSubjectServiceTest {
    @Autowired
    private MicroDegreeSubjectService microDegreeSubjectService;

    @Autowired
    private MicroDegreeSubjectRepository microDegreeSubjectRepository;
    @Autowired
    private MicroDegreeRepository microDegreeRepository;
    @Autowired
    private MicroDegreeService microDegreeService;

    @BeforeEach
    void setUp() { microDegreeSubjectRepository.deleteAll(); }

    @Test
    @DisplayName("microDegreeSubject 등록 성공 테스트")
    void mdsubject등록_성공() throws Exception {
        // given
        MicroDegree md = MicroDegree.builder()
                .title("반도체융합기술")
                .description("차세대 기능성 반도체 소재 및 소자 공정에 대한 이해. 미래지향적 반도체/디스플레이 구동 원리 및 소자에 관한 이해. 이를 기반으로 한 첨단 반도체/디스플레이 응용분야 발굴 및 적용")
                .category(MicroDegreeCategory.CONVERGENCE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        microDegreeRepository.save(md);

        MicroDegreeSubjectRequestDto mdSubject = new MicroDegreeSubjectRequestDto();
        mdSubject.setTitle("인공지능·빅데이터의이해");
        mdSubject.setDescription("AI 학부를 전공하는 학생들이 기본적으로 이해해야 할 제반 분야를 파악하고, 특히 인공지능 및 빅데이터의 개념, 문제해결 방법, 그리고 다양한 분야에 관한 기초적인 지식을 습득한다.");
        mdSubject.setCategory(md.getTitle());
        mdSubject.setSubjectNum(550157);
        mdSubject.setClasses("기초전공");
        mdSubject.setCredit(3);

        // when
        microDegreeSubjectService.createMicroDegreeSubject(mdSubject);

        // then
        Optional<MicroDegreeSubject> savedMDSubject = microDegreeSubjectRepository.findByTitle(mdSubject.getTitle());
        log.info("savedMDSubject: {}", savedMDSubject);
        assertTrue(savedMDSubject.isPresent());
    }

    @Test
    @DisplayName("microDegreeSubject 수정 성공 테스트")
    void mdsubject수정_성공() throws Exception {
        // given
        MicroDegree md = MicroDegree.builder()
                .title("반도체융합기술")
                .description("차세대 기능성 반도체 소재 및 소자 공정에 대한 이해. 미래지향적 반도체/디스플레이 구동 원리 및 소자에 관한 이해. 이를 기반으로 한 첨단 반도체/디스플레이 응용분야 발굴 및 적용")
                .category(MicroDegreeCategory.CONVERGENCE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        microDegreeRepository.save(md);

        MicroDegreeSubject microDegreeSubject = MicroDegreeSubject.builder()
                .title("인공지능·빅데이터의이해")
                .description("AI 학부를 전공하는 학생들이 기본적으로 이해해야 할 제반 분야를 파악하고, 특히 인공지능 및 빅데이터의 개념, 문제해결 방법, 그리고 다양한 분야에 관한 기초적인 지식을 습득한다.")
                .category(md.getTitle())
                .subjectNum(550157)
                .classes("기초전공")
                .credit(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        int id = microDegreeSubjectRepository.save(microDegreeSubject).getId();

        // when
        MicroDegreeSubjectRequestDto dto = new MicroDegreeSubjectRequestDto();
        dto.setTitle("인공지능");
        dto.setDescription("description");
        dto.setCategory(md.getTitle());
        dto.setSubjectNum(550157);
        dto.setClasses("기초전공");
        dto.setCredit(3);
        microDegreeSubjectService.updateMicroDegreeSubject(id, dto);

        // then
        assertTrue(microDegreeSubjectRepository.findById(id).get().getDescription().equals(dto.getDescription()));
    }

    @Test
    @DisplayName("microDegreeSubject 삭제 성공 테스트")
    void microdegreeSubject삭제_성공() throws Exception {
        // given
        MicroDegree md = MicroDegree.builder()
                .title("반도체융합기술")
                .description("차세대 기능성 반도체 소재 및 소자 공정에 대한 이해. 미래지향적 반도체/디스플레이 구동 원리 및 소자에 관한 이해. 이를 기반으로 한 첨단 반도체/디스플레이 응용분야 발굴 및 적용")
                .category(MicroDegreeCategory.CONVERGENCE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        microDegreeRepository.save(md);

        MicroDegreeSubject microDegreeSubject = MicroDegreeSubject.builder()
                .title("인공지능·빅데이터의이해")
                .description("AI 학부를 전공하는 학생들이 기본적으로 이해해야 할 제반 분야를 파악하고, 특히 인공지능 및 빅데이터의 개념, 문제해결 방법, 그리고 다양한 분야에 관한 기초적인 지식을 습득한다.")
                .category(md.getTitle())
                .subjectNum(550157)
                .classes("기초전공")
                .credit(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        int id = microDegreeSubjectRepository.save(microDegreeSubject).getId();

        // when
        microDegreeSubjectService.deleteMicroDegreeSubject(id);

        // then
        assertTrue(microDegreeSubjectRepository.findById(id).isEmpty());
    }
}