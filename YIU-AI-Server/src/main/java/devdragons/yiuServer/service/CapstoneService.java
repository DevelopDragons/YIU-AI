package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.CapstoneRequestDto;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CapstoneRepository;
import devdragons.yiuServer.repository.FilesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CapstoneService {
    private final CapstoneRepository capstoneRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
    * @description Capstone 팀 등록
    * @author 김예서
    * @param teamName, professor, topic, description, link, thumbnail
    * @return Boolean
    * */
    public Boolean createCapstone(CapstoneRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getTeamName(), requestDto.getProfessor(), requestDto.getTopic(),
                requestDto.getDescription(), requestDto.getLink()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            Capstone capstone = Capstone.builder()
                    .teamName(requestDto.getTeamName())
                    .professor(requestDto.getProfessor())
                    .topic(requestDto.getTopic())
                    .description(requestDto.getDescription())
                    .link(requestDto.getLink())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Capstone savedCapstone = capstoneRepository.save(capstone);

            if(requestDto.getThumbnail() != null) {
                List<FileRequestDto> thumbnails = fileService.uploadFiles(requestDto.getThumbnail());

                fileService.saveFiles(FileType.CAPSTONE, savedCapstone.getId(), "thumbnail", thumbnails);
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
