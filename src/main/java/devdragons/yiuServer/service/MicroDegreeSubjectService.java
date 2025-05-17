package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.MicroDegreeSubject;
import devdragons.yiuServer.dto.request.MicroDegreeSubjectRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MicroDegreeRepository;
import devdragons.yiuServer.repository.MicroDegreeSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MicroDegreeSubjectService {
    private final MicroDegreeSubjectRepository microDegreeSubjectRepository;
    private final MicroDegreeRepository microDegreeRepository;

    /*
     * @description microDegreeSubject 등록
     * @author 김예서
     * @param title, description, category
     * @return Boolean
     * */
    public Boolean createMicroDegreeSubject(MicroDegreeSubjectRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getCategory(),
                requestDto.getSubjectNum(), requestDto.getClasses(), requestDto.getCredit()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        if(!microDegreeRepository.existsByTitle(requestDto.getCategory())) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }

        Optional<MicroDegree> md = microDegreeRepository.findByTitle(requestDto.getCategory());

        try {
            MicroDegreeSubject mdSubject = MicroDegreeSubject.builder()
                    .title(requestDto.getTitle())
                    .description(requestDto.getDescription())
                    .category(md.get().getTitle())
                    .subjectNum(requestDto.getSubjectNum())
                    .classes(requestDto.getClasses())
                    .credit(requestDto.getCredit())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            microDegreeSubjectRepository.save(mdSubject);

            return true;
        } catch (Exception e) {
            log.error("에러메시지: " +e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description microDegreeSubject 등록
     * @author 김예서
     * @param id, title, description, category
     * @return Boolean
     * */
    public Boolean updateMicroDegreeSubject(Integer id, MicroDegreeSubjectRequestDto requestDto) throws Exception {
        MicroDegreeSubject md = microDegreeSubjectRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getCategory(),
                requestDto.getSubjectNum(), requestDto.getClasses(), requestDto.getCredit()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        if(!microDegreeRepository.existsByTitle(requestDto.getCategory())) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }

        Optional<MicroDegree> microDegree = microDegreeRepository.findByTitle(requestDto.getCategory());

        try {
            md.setTitle(requestDto.getTitle());
            md.setDescription(requestDto.getDescription());
            md.setCategory(microDegree.get().getTitle());
            md.setSubjectNum(requestDto.getSubjectNum());
            md.setClasses(requestDto.getClasses());
            md.setCredit(requestDto.getCredit());
            md.setUpdatedAt(LocalDateTime.now());

            microDegreeSubjectRepository.save(md);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description microDegreeSubject 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteMicroDegreeSubject(Integer id) throws Exception {
        MicroDegreeSubject md = microDegreeSubjectRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));
        microDegreeSubjectRepository.delete(md);
        return true;
    }
}
