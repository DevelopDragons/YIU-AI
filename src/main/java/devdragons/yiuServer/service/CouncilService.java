package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Council;
import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.CouncilRequestDto;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.response.CouncilResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CouncilRepository;
import devdragons.yiuServer.repository.FilesRepository;
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
public class CouncilService {
    private final CouncilRepository councilRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

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

            Council savedCouncil = councilRepository.save(council);

            List<FileRequestDto> thumnails = fileService.uploadFiles(requestDto.getThumbnails());
            List<FileRequestDto> people = fileService.uploadFiles(requestDto.getPeople());

            fileService.saveFiles(FileType.COUNCIL, savedCouncil.getId(), "thumbnail", thumnails);
            fileService.saveFiles(FileType.COUNCIL, savedCouncil.getId(), "people", people);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
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

            // 기존 파일 삭제(disk)
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.COUNCIL, id);
            System.out.println("deleteFiles = " + deleteFiles);

            // 기존 파일 삭제(db)
            filesRepository.deleteAllByTypeAndTypeId(FileType.COUNCIL, id);

            // 수정 파일 업로드
            List<FileRequestDto> thumnails = fileService.uploadFiles(requestDto.getThumbnails());
            List<FileRequestDto> people = fileService.uploadFiles(requestDto.getPeople());

            fileService.saveFiles(FileType.COUNCIL, id, "thumbnail", thumnails);
            fileService.saveFiles(FileType.COUNCIL, id, "people", people);

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
            // 기존 파일 삭제(disk)
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.COUNCIL, id);
            fileService.deleteFiles(deleteFiles);

            // 기존 파일 삭제(db)
            filesRepository.deleteAllByTypeAndTypeId(FileType.COUNCIL, id);

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
        List<Files> thumbnails = filesRepository.findAllByTypeAndCategory(FileType.COUNCIL, "thumbnail");
        List<Files> people = filesRepository.findAllByTypeAndCategory(FileType.COUNCIL, "people");

        List<CouncilResponseDto> getListDto = new ArrayList<>();
        for(Council council : councils) {
            List<Files> filteredThumbnails = thumbnails.stream()
                            .filter(files -> files.getTypeId().equals(council.getId()))
                            .collect(Collectors.toList());
            List<Files> filteredPeople = people.stream()
                            .filter(files -> files.getTypeId().equals(council.getId()))
                            .collect(Collectors.toList());
            getListDto.add(CouncilResponseDto.GetCouncilDto(council, filteredThumbnails, filteredPeople));
        }

        return getListDto;
    }
}
