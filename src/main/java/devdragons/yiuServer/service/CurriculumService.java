package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Curriculum;
import devdragons.yiuServer.dto.request.CurriculumRequestDto;
import devdragons.yiuServer.dto.response.CurriculumResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CurriculumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;

    /*
     * @description 커리큘럼 등록
     * @author 김예서
     * @param title, course, grade, term, credit, classes, description
     * @return Boolean
     * */
    public Boolean createCurriculum(CurriculumRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getCourse(), requestDto.getGrade(), requestDto.getCode(),
                requestDto.getTerm(), requestDto.getCredit(), requestDto.getClasses(), requestDto.getPractice(), requestDto.getTheory(), requestDto.getDescription()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            Curriculum curriculum = Curriculum.builder()
                    .title(requestDto.getTitle())
                    .description(requestDto.getDescription())
                    .term(requestDto.getTerm())
                    .credit(requestDto.getCredit())
                    .classes(requestDto.getClasses())
                    .grade(requestDto.getGrade())
                    .course(requestDto.getCourse())
                    .practice(requestDto.getPractice())
                    .theory(requestDto.getTheory())
                    .code(requestDto.getCode())
                    .createdAt(requestDto.getCreatedAt())
                    .updatedAt(requestDto.getUpdatedAt())
                    .build();
            curriculumRepository.save(curriculum);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 커리큘럼 수정
     * @author 김예서
     * @param id, title, course, grade, term, credit, classes, description
     * @return Boolean
     * */
    public Boolean updateCurriculum(Integer id, CurriculumRequestDto requestDto) throws Exception {
        Curriculum curriculum = curriculumRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getCourse(), requestDto.getGrade(), requestDto.getCode(),
                requestDto.getTerm(), requestDto.getCredit(), requestDto.getClasses(), requestDto.getPractice(), requestDto.getTheory(), requestDto.getDescription()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            curriculum.setTitle(requestDto.getTitle());
            curriculum.setCourse(requestDto.getCourse());
            curriculum.setGrade(requestDto.getGrade());
            curriculum.setTerm(requestDto.getTerm());
            curriculum.setCredit(requestDto.getCredit());
            curriculum.setClasses(requestDto.getClasses());
            curriculum.setPractice(requestDto.getPractice());
            curriculum.setTheory(requestDto.getTheory());
            curriculum.setCode(requestDto.getCode());
            curriculum.setDescription(requestDto.getDescription());
            curriculum.setUpdatedAt(requestDto.getUpdatedAt());
            curriculumRepository.save(curriculum);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 커리큘럼 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteCurriculum(Integer id) throws Exception {
        Curriculum curriculum = curriculumRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            curriculumRepository.delete(curriculum);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 커리큘럼 조회
     * @author 김예서
     * @return List<CurriculumResponseDto>
     * */
    public List<CurriculumResponseDto> getCurriculum() throws Exception {
        List<Curriculum> curriculums = curriculumRepository.findAll();

        List<CurriculumResponseDto> getListDto = new ArrayList<>();
        for (Curriculum curriculum : curriculums) {
            getListDto.add(CurriculumResponseDto.GetCurriculumDto(curriculum));
        }

        return getListDto;
    }
}
