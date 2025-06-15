package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Graduation;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.GraduationRequestDto;
import devdragons.yiuServer.dto.response.GraduationResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.GraduationRepository;
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
public class GraduationService {
    private final GraduationRepository graduationRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description Graduation 등록
     * @author 김예서
     * @param year, file
     * @return Boolean
     * */
    public Boolean createGraduation(GraduationRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getYear(), requestDto.getFile()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            Graduation graduation = Graduation.builder()
                    .year(requestDto.getYear())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Graduation savedGraduation = graduationRepository.save(graduation);

            List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());

            fileService.saveFiles(FileType.GRADUATE, savedGraduation.getYear(), "file", file);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description graduation 수정
     * @author 김예서
     * @param year, file
     * @return Boolean
     * */
    public Boolean updateGraduation(Integer year, GraduationRequestDto requestDto) throws Exception {
        Graduation graduation = graduationRepository.findByYear(year);

        List<Object> requiredFields = Arrays.asList(
                requestDto.getYear(), requestDto.getFile()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            graduation.setYear(requestDto.getYear());
            graduation.setUpdatedAt(LocalDateTime.now());

            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GRADUATE, year);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.GRADUATE, year);

            List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());
            fileService.saveFiles(FileType.GRADUATE, graduation.getYear(), "file", file);

            graduationRepository.save(graduation);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description graduation 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteGraduation(Integer year) throws Exception {
        Graduation graduation = graduationRepository.findByYear(year);

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GRADUATE, year);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.GRADUATE, year);
            graduationRepository.delete(graduation);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public List<GraduationResponseDto> getGraduation() throws Exception {
        List<Graduation> graduations = graduationRepository.findAll();
        List<Files> graduationFile = filesRepository.findAllByTypeAndCategory(FileType.GRADUATE, "file");

        List<GraduationResponseDto> getListDto = new ArrayList<>();
        for(Graduation graduation : graduations) {
            List<Files> fileteredFiles = graduationFile.stream()
                    .filter(files -> files.getTypeId().equals(graduation.getYear()))
                    .collect(Collectors.toList());

            getListDto.add(GraduationResponseDto.GetGrauduationDto(graduation, fileteredFiles));
        }
        return getListDto;
    }
}
