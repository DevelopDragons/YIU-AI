package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Greeting;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.GreetingRequestDto;
import devdragons.yiuServer.dto.response.GreetingResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.GreetingRepository;
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
public class GreetingService {
    private final GreetingRepository greetingRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    /*
     * @description 학부장 인사말 등록
     * @author 김예서
     * @param name, greetings, image, autograph
     * @return Boolean
     * */
    public Boolean createGreeting(GreetingRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getGreetings()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            Greeting greeting = Greeting.builder()
                    .name(requestDto.getName())
                    .greetings(requestDto.getGreetings())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Greeting savedGreeting = greetingRepository.save(greeting);

            if(requestDto.getImage() != null || requestDto.getAutograph() != null) {
                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                List<FileRequestDto> autograph = fileService.uploadFiles(requestDto.getAutograph());

                fileService.saveFiles(FileType.GREETING, savedGreeting.getId(), "image", image);
                fileService.saveFiles(FileType.GREETING, savedGreeting.getId(), "autograph", autograph);
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학부장 인사말 수정
     * @author 김예서
     * @param id, name, greetings, image, autograph
     * @return Boolean
     * */
    public Boolean updateGreeting(Integer id, GreetingRequestDto requestDto) throws Exception {
        Greeting greeting = greetingRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getGreetings()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            greeting.setName(requestDto.getName());
            greeting.setGreetings(requestDto.getGreetings());
            greeting.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getImage() != null || requestDto.getAutograph() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GREETING, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.GREETING, id);

                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                List<FileRequestDto> autograph = fileService.uploadFiles(requestDto.getAutograph());

                fileService.saveFiles(FileType.GREETING, id, "image", image);
                fileService.saveFiles(FileType.GREETING, id, "autograph", autograph);
            }
            greetingRepository.save(greeting);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학부장 인사말 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteGreeting(Integer id) throws Exception {
        Greeting greeting = greetingRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.GREETING, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.GREETING, id);

            greetingRepository.delete(greeting);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 학부장 인사말 조회
     * @author 김예서
     * @return List<GreetingResponseDto>
     * */
    public List<GreetingResponseDto> getGreeting() throws Exception {
        List<Greeting> greetings = greetingRepository.findAll();
        List<Files> image = filesRepository.findAllByTypeAndCategory(FileType.GREETING, "image");
        List<Files> autograph = filesRepository.findAllByTypeAndCategory(FileType.GREETING, "autograph");

        List<GreetingResponseDto> getListDto = new ArrayList<>();
        for(Greeting greeting : greetings) {
            List<Files> filteredImage = image.stream()
                    .filter(files -> files.getTypeId().equals(greeting.getId()))
                    .collect(Collectors.toList());
            List<Files> filteredAutograph = autograph.stream()
                    .filter(files -> files.getTypeId().equals(greeting.getId()))
                    .collect(Collectors.toList());
            getListDto.add(GreetingResponseDto.GetGreetingDto(greeting, filteredImage, filteredAutograph));
        }
        return getListDto;
    }
}
