package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Info;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.InfoRequestDto;
import devdragons.yiuServer.dto.response.InfoResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.InfoRepository;
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
public class InfoService {
    private final InfoRepository infoRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description info 등록
     * @author 김예서
     * @param name, engName, address, tel, mail, professor, greeting, bossImage, title, contents, image
     * @return Boolean
     * */
    public Boolean createInfo(InfoRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getEngName(), requestDto.getAddress(),
                requestDto.getTel(), requestDto.getMail(), requestDto.getProfessor(), requestDto.getGreeting(),
                requestDto.getTitle(), requestDto.getContents()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            Info info = Info.builder()
                    .name(requestDto.getName())
                    .engName(requestDto.getEngName())
                    .address(requestDto.getAddress())
                    .tel(requestDto.getTel())
                    .mail(requestDto.getMail())
                    .professor(requestDto.getProfessor())
                    .greeting(requestDto.getGreeting())
                    .title(requestDto.getTitle())
                    .contents(requestDto.getContents())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Info savedInfo = infoRepository.save(info);

            if(requestDto.getBossImage() != null || requestDto.getImage() != null) {
                List<FileRequestDto> bossImage = fileService.uploadFiles(requestDto.getBossImage());
                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());

                fileService.saveFiles(FileType.INFO, savedInfo.getId(), "bossImage", bossImage);
                fileService.saveFiles(FileType.INFO, savedInfo.getId(), "image", image);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description info 수정
     * @author 김예서
     * @param id, name, engName, address, tel, mail, professor, greeting, bossImage, title, contents, image
     * @return Boolean
     * */
    public Boolean updateInfo(Integer id, InfoRequestDto requestDto) throws Exception {
        Info info = infoRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getEngName(), requestDto.getAddress(),
                requestDto.getTel(), requestDto.getMail(), requestDto.getProfessor(), requestDto.getGreeting(),
                requestDto.getTitle(), requestDto.getContents()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            info.setName(requestDto.getName());
            info.setEngName(requestDto.getEngName());
            info.setAddress(requestDto.getAddress());
            info.setTel(requestDto.getTel());
            info.setMail(requestDto.getMail());
            info.setProfessor(requestDto.getProfessor());
            info.setGreeting(requestDto.getGreeting());
            info.setTitle(requestDto.getTitle());
            info.setContents(requestDto.getContents());
            info.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getBossImage() != null || requestDto.getImage() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.INFO, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.INFO, id);

                List<FileRequestDto> bossImage = fileService.uploadFiles(requestDto.getBossImage());
                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());

                fileService.saveFiles(FileType.INFO, id, "bossImage", bossImage);
                fileService.saveFiles(FileType.INFO, id, "image", image);
            }
            infoRepository.save(info);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description info 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteInfo(Integer id) throws Exception {
        Info info = infoRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.INFO, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.INFO, id);

            infoRepository.delete(info);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description info 조회
     * @author 김예서
     * @return List<InfoResponseDto>
     * */
    public List<InfoResponseDto> getInfos() throws Exception {
        List<Info> infos = infoRepository.findAll();
        List<Files> bossImage = filesRepository.findAllByTypeAndCategory(FileType.INFO, "bossImage");
        List<Files> image = filesRepository.findAllByTypeAndCategory(FileType.INFO, "image");

        List<InfoResponseDto> getListDto = new ArrayList<>();
        for(Info info : infos) {
            List<Files> filteredBossImage = bossImage.stream()
                    .filter(files -> files.getTypeId().equals(info.getId()))
                    .collect(Collectors.toList());
            List<Files> filteredImage = image.stream()
                    .filter(files -> files.getTypeId().equals(info.getId()))
                    .collect(Collectors.toList());
            getListDto.add(InfoResponseDto.GetInfoDto(info, filteredBossImage, filteredImage));
        }
        return getListDto;
    }
}
