package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.dto.request.MicroDegreeRequestDto;
import devdragons.yiuServer.dto.response.MicroDegreeResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.MicroDegreeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MicroDegreeService {
    private final MicroDegreeRepository microDegreeRepository;

    /*
     * @description md 등록
     * @author 김예서
     * @param name, title, description, required, category
     * @return int
     * */
    public int createMd(MicroDegreeRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getTitle(), requestDto.getDescription(),
                requestDto.getRequired(), requestDto.getCategory()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            MicroDegree microDegree = MicroDegree.builder()
                    .name(requestDto.getName())
                    .title(requestDto.getTitle())
                    .description(requestDto.getDescription())
                    .required(requestDto.getRequired())
                    .category(requestDto.getCategory())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            return microDegreeRepository.save(microDegree).getId();
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

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getTitle(), requestDto.getDescription(),
                requestDto.getRequired(), requestDto.getCategory()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            microDegree.setName(requestDto.getName());
            microDegree.setTitle(requestDto.getTitle());
            microDegree.setDescription(requestDto.getDescription());
            microDegree.setRequired(requestDto.getRequired());
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
    public List<MicroDegreeResponseDto> getMd() throws Exception {
        List<MicroDegree> mds = microDegreeRepository.findAll();

        List<MicroDegreeResponseDto> getListDto = new ArrayList<>();
        for(MicroDegree md : mds) {
            getListDto.add(MicroDegreeResponseDto.GetMicroDegreeDto(md));
        }
        return getListDto;
    }
}
