package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.GraduateSchool;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.GraduateSchoolRequestDto;
import devdragons.yiuServer.dto.response.GraduateSchoolResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.GraduateSchoolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GraduateSchoolService {
    private final GraduateSchoolRepository graduateSchoolRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description 대학원 등록
     * @author 김예서
     * @param name, slogan, image
     * @return Boolean
     * */
    public Boolean createGraduateSchool(GraduateSchoolRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getSlogan()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            GraduateSchool graduateSchool = GraduateSchool.builder()
                    .name(requestDto.getName())
                    .slogan(requestDto.getSlogan())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            GraduateSchool savedGraduateSchool = graduateSchoolRepository.save(graduateSchool);

            if(requestDto.getImage() != null) {
                List<FileRequestDto> images = fileService.uploadFiles(requestDto.getImage());
                fileService.saveFiles(FileType.GRADUATESCHOOL, savedGraduateSchool.getId(), "image", images);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 대학원 수정
     * @author 김예서
     * @param id, name, slogan, image
     * @return Boolean
     * */
    public Boolean updateGraduateSchool(Integer id, GraduateSchoolRequestDto requestDto) throws Exception {
        GraduateSchool graduateSchool = graduateSchoolRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getSlogan()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            graduateSchool.setName(requestDto.getName());
            graduateSchool.setSlogan(requestDto.getSlogan());
            graduateSchool.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getImage() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GRADUATESCHOOL, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.GRADUATESCHOOL, id);

                List<FileRequestDto> images = fileService.uploadFiles(requestDto.getImage());
                fileService.saveFiles(FileType.GRADUATESCHOOL, id, "image", images);
            }
            graduateSchoolRepository.save(graduateSchool);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 대학원 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteGraduateSchool(Integer id) throws Exception {
        GraduateSchool graduateSchool = graduateSchoolRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GRADUATESCHOOL, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.GRADUATESCHOOL, id);

            graduateSchoolRepository.delete(graduateSchool);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 대학원 조회
     * @author 김예서
     * @return List<GraduateSchoolResponseDto>
     * */
    public List<GraduateSchoolResponseDto> getGraduateSchool() throws Exception {
        List<GraduateSchool> graduateSchools = graduateSchoolRepository.findAll();
        List<Files> images = filesRepository.findAllByTypeAndCategory(FileType.GRADUATESCHOOL, "image");

        List<GraduateSchoolResponseDto> getListDto = new ArrayList<>();
        for(GraduateSchool graduateSchool : graduateSchools) {
            List<Files> filteredImages = images.stream()
                    .filter(files -> files.getTypeId().equals(graduateSchool.getId()))
                    .collect(Collectors.toList());
            getListDto.add(GraduateSchoolResponseDto.GetGraduateSchoolDto(graduateSchool, filteredImages));
        }

        return getListDto;
    }
}
