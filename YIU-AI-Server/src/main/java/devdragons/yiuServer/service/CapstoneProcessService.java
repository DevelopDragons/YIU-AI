package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.CapstoneProcess;
import devdragons.yiuServer.domain.state.CapstoneProcessCategory;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.domain.state.StatusCategory;
import devdragons.yiuServer.dto.request.CapstoneProcessRequestDto;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CapstoneProcessRepository;
import devdragons.yiuServer.repository.CapstoneRepository;
import devdragons.yiuServer.repository.FilesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CapstoneProcessService {
    private final CapstoneProcessRepository capstoneProcessRepository;
    private final CapstoneRepository capstoneRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description 캡스톤 프로세스 등록 (전체)
     * @author 김예서
     * @param category, description
     * @return Boolean
     * */
    @Transactional
    public Boolean createCapstoneProcess(CapstoneProcessRequestDto requestDto) throws Exception {
        // 데이터 입력 점검
        List<Object> requiredFields = Arrays.asList(
                  requestDto.getCategory(), requestDto.getDescription()
        );

        CommonService.validateRequiredFields(requiredFields);

        // 입력 연도의 캡스톤 팀 데이터 가져오기
        int year = LocalDate.now().getYear();

        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
        List<Capstone> capstones = capstoneRepository.findAllByCreatedAtBetween(start, end);

        try {
            List<CapstoneProcess> processList = new ArrayList<>();
            for (Capstone capstone : capstones) {
                processList.add(CapstoneProcess.builder()
                        .capstone(capstone)
                        .category(requestDto.getCategory())
                        .status(StatusCategory.NOT_SUBMITTED)
                        .description(requestDto.getDescription())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
            capstoneProcessRepository.saveAll(processList);
        } catch (Exception e) {
            log.error("Failed to create capstone process", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    /*
     * @description 캡스톤 프로세스 등록 (특정 팀)
     * @author 김예서
     * @param capstoneId, description
     * @return Boolean
     * */
    public Boolean createCapstoneProcessForOneTeam(CapstoneProcessRequestDto requestDto, int capstoneId) throws Exception {
        Capstone capstone = capstoneRepository.findById(capstoneId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        });

        // 데이터 입력 점검
        List<Object> requiredFields = Arrays.asList(
                requestDto.getDescription()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            CapstoneProcess capstoneProcess = CapstoneProcess.builder()
                    .capstone(capstone)
                    .category(CapstoneProcessCategory.ETC)
                    .status(StatusCategory.NOT_SUBMITTED)
                    .description(requestDto.getDescription())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            capstoneProcessRepository.save(capstoneProcess);
        } catch (Exception e) {
            log.error("Failed to create capstone process", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    /*
     * @description 캡스톤 과제 제출
     * @author 김예서
     * @param contents, link, file
     * @return Boolean
     * */
    public Boolean submitCapstoneProcess(CapstoneProcessRequestDto requestDto, int id) throws Exception {
        CapstoneProcess capstoneProcess = capstoneProcessRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        });

        // 데이터 입력 점검
        List<Object> requiredFields = Arrays.asList(
                requestDto.getContents()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            capstoneProcess.setStatus(StatusCategory.SUBMITTED);
            capstoneProcess.setContents(requestDto.getContents());
            capstoneProcess.setLink(requestDto.getLink());
            capstoneProcess.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getFile() != null) {
                List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());

                fileService.saveFiles(FileType.CAPSTONEPROCESS, id, "file", file);
            }

            capstoneProcessRepository.save(capstoneProcess);
        } catch (Exception e) {
            log.error("Failed to submit capstone process", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    /*
     * @description 캡스톤 과제 승인 / 반려
     * @author 김예서
     * @param status, feedback
     * @return Boolean
     * */
    public Boolean passOrFailCapstoneProcess(CapstoneProcessRequestDto requestDto, int id) throws Exception {
        CapstoneProcess capstoneProcess = capstoneProcessRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        });

        // 데이터 입력 점검
        List<Object> requiredFields = Arrays.asList(
                requestDto.getStatus(), requestDto.getFeedback()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            capstoneProcess.setStatus(requestDto.getStatus());
            capstoneProcess.setFeedback(requestDto.getFeedback());
            capstoneProcess.setUpdatedAt(LocalDateTime.now());
            capstoneProcessRepository.save(capstoneProcess);
        } catch (Exception e) {
            log.error("Failed to submit capstone process", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
}
