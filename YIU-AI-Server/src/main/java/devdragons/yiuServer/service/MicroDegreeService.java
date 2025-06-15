package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.MicroDegreeSubject;
import devdragons.yiuServer.dto.request.MicroDegreeRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MicroDegreeRepository;
import devdragons.yiuServer.repository.MicroDegreeSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MicroDegreeService {
    private final MicroDegreeRepository microDegreeRepository;
    private final MicroDegreeSubjectRepository microDegreeSubjectRepository;

    /*
     * @description md 등록
     * @author 김예서
     * @param title, description, category
     * @return Boolean
     * */
    public Boolean createMd(MicroDegreeRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getCategory()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            MicroDegree microDegree = MicroDegree.builder()
                    .title(requestDto.getTitle())
                    .description(requestDto.getDescription())
                    .category(requestDto.getCategory())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            microDegreeRepository.save(microDegree);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description md 수정
     * @author 김예서
     * @param id, name, title, description, required, category
     * @return Boolean
     * */
    public Boolean updateMd(Integer id, MicroDegreeRequestDto requestDto) throws Exception {
        MicroDegree microDegree = microDegreeRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getCategory()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            microDegree.setTitle(requestDto.getTitle());
            microDegree.setDescription(requestDto.getDescription());
            microDegree.setCategory(requestDto.getCategory());
            microDegree.setUpdatedAt(LocalDateTime.now());

            microDegreeRepository.save(microDegree);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description md 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteMd(Integer id) throws Exception {
        MicroDegree md = microDegreeRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));
        microDegreeRepository.delete(md);
        return true;
    }

    /*
     * @description md 조회
     * @author 김예서
     * @return List<MicroDegreeResponseDto>
     * */
    public List<MicroDegreeResponseDto> getMdList() throws Exception {
        List<MicroDegree> microDegrees = microDegreeRepository.findAll();

        return microDegrees.stream()
                .map(microDegree -> {
                    List<MicroDegreeSubject> subjectList = microDegreeSubjectRepository
                            .findByCategory(microDegree.getTitle());

                    return MicroDegreeResponseDto.GetMicroDegreeDto(microDegree, subjectList);
                })
                .collect(Collectors.toList());
    }
}
