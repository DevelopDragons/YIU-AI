package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Council;
import devdragons.yiuServer.dto.request.CouncilRequestDto;
import devdragons.yiuServer.dto.response.CouncilResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CouncilRepository;
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
public class CouncilService {
    private final CouncilRepository councilRepository;

    /*
     * @description 학생회 등록
     * @author 김예서
     * @param name, link, year, slogan, description
     * @return
     * */
    public Boolean createCouncil(CouncilRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getLink(), requestDto.getYear(),
                requestDto.getSlogan(), requestDto.getDescription()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            Council council = Council.builder()
                    .name(requestDto.getName())
                    .link(requestDto.getLink())
                    .year(requestDto.getYear())
                    .slogan(requestDto.getSlogan())
                    .description(requestDto.getDescription())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            councilRepository.save(council);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학생회 수정
     * @author 김예서
     * @param id, name, link, year, slogan, description
     * @return
     * */
    public Boolean updateCouncil(Integer id, CouncilRequestDto requestDto) throws Exception {
        Council council = councilRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getLink(), requestDto.getYear(),
                requestDto.getSlogan(), requestDto.getDescription()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            council.setName(requestDto.getName());
            council.setLink(requestDto.getLink());
            council.setYear(requestDto.getYear());
            council.setSlogan(requestDto.getSlogan());
            council.setDescription(requestDto.getDescription());
            council.setUpdatedAt(LocalDateTime.now());

            councilRepository.save(council);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학생회 삭제
     * @author 김예서
     * @param id
     * @return
     * */
    public Boolean deleteCouncil(Integer id) throws Exception {
        Council council = councilRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            councilRepository.delete(council);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학생회 조회
     * @author 김예서
     * @return
     * */
    public List<CouncilResponseDto> getCouncil() throws Exception {
        List<Council> councils = councilRepository.findAll();

        List<CouncilResponseDto> getListDto = new ArrayList<>();
        for(Council council : councils) {
            getListDto.add(CouncilResponseDto.GetCouncilDto(council));
        }

        return getListDto;
    }
}
