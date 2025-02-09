package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.MOU;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.MouRequestDto;
import devdragons.yiuServer.dto.response.FileResponseDto;
import devdragons.yiuServer.dto.response.MouResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.MouRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MouService {
    private final MouRepository mouRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description mou 등록
     * @author 김예서
     * @param name, image
     * @return Boolean
     * */
    public Boolean createMou(MouRequestDto requestDto) throws Exception {
        if(requestDto.getName().isEmpty()){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            MOU mou = MOU.builder()
                    .name(requestDto.getName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            MOU savedMou = mouRepository.save(mou);

            if(requestDto.getImage() != null) {
                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                fileService.saveFiles(FileType.MOU, savedMou.getId(), "image", image);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description mou 수정
     * @author 김예서
     * @param id, name, image
     * @return Boolean
     * */
    public Boolean updateMou(Integer id, MouRequestDto requestDto) throws Exception {
        MOU mou = mouRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        if(requestDto.getName().isEmpty()){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            mou.setName(requestDto.getName());

            if(requestDto.getImage() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.MOU, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.MOU, id);

                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                fileService.saveFiles(FileType.MOU, id, "image", image);
            }
            mouRepository.save(mou);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description mou 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteMou(Integer id) throws Exception {
        MOU mou = mouRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.MOU, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.MOU, id);

            mouRepository.delete(mou);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description mou 조회
     * @author 김예서
     * @return List<MouResponseDto>
     * */
    public List<MouResponseDto> getMou() throws Exception {
        List<MOU> mous = mouRepository.findAll();
        List<Files> image = filesRepository.findAllByTypeAndCategory(FileType.MOU, "image");

        List<MouResponseDto> getListDto = new ArrayList<>();
        for(MOU mou : mous){
            List<Files> filteredImages = image.stream()
                    .filter(files -> files.getTypeId().equals(mou.getId()))
                    .collect(Collectors.toList());

            getListDto.add(MouResponseDto.GetMouDto(mou, filteredImages));
        }
        return getListDto;
    }
}
