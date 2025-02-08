package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.repository.FilesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FilesRepository fileRepository;

    private final String uploadPath = Paths.get("/Users", "yeseokim", "dev", "upload-files").toString();


    /*
     * @description 파일 저장
     * @author 김예서
     * @param type, typeId, fileRequestDto
     * @return
     * */
    public void saveFiles(FileType type, int typeId, String category, List<FileRequestDto> files) {
        if(CollectionUtils.isEmpty(files)) {
            return;
        }
        List<Files> fileEntities = files.stream()
                .map(fileDto -> Files.builder()
                        .type(type)
                        .typeId(typeId)
                        .category(category)
                        .originName(fileDto.getOriginName())
                        .saveName(fileDto.getSaveName())
                        .size(fileDto.getSize())
                        .createdAt(LocalDateTime.now())
                        .build()
                )
                .collect(Collectors.toList());


        fileRepository.saveAll(fileEntities);
    }

    /*
     * @description 다중 파일 업로드
     * @author 김예서
     * @param multipartFiles(다중 파일)
     * @return
     * */
    public List<FileRequestDto> uploadFiles(List<MultipartFile> multipartFiles) throws IllegalStateException, IOException {
        List<FileRequestDto> files = new ArrayList<>();
        try {
            for(MultipartFile multipartFile : multipartFiles) {
                if(multipartFile.isEmpty()) {
                    continue;
                }
                files.add(uploadFile(multipartFile));
            }
            return files;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    /*
     * @description 단일 파일 업로드
     * @author 김예서
     * @param multipartFile(단일 파일)
     * @return
     * */
    public FileRequestDto uploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String saveName = multipartFile.getOriginalFilename();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileRequestDto.builder()
                .originName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .size(multipartFile.getSize())
                .build();
    }

    /*
     * @description 저장 파일명 생성
     * @author 김예서
     * @param fileName 원본 파일명
     * @return
     * */
    private String generateSaveFileName(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(fileName);
        return uuid + "." + extension;
    }

    /*
     * @description 업로드 경로 반환
     * @author 김예서
     * @return 업로드 경로
     * */
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    /*
     * @description 업로드 경로 반환
     * @author 김예서
     * @param addPath - 추가 경로
     * @return 업로드 경로
     * */
    private String getUploadPath(String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    /*
     * @description 업로드 폴더 생성
     * @author 김예서
     * @param path - 업로드 경로
     * @return 업로드 경로
     * */
    private String makeDirectories(String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }
}
