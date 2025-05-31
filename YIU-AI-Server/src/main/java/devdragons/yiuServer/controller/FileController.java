package devdragons.yiuServer.controller;

import com.google.common.net.HttpHeaders;
import java.nio.file.Files;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FilesRepository filesRepository;
    private final Path uploadPath = Paths.get("/Users", "yeseokim", "dev", "upload-files");

    public Resource readFileAsResource(devdragons.yiuServer.domain.Files file) {
        String uploadedDate = file.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String filename = file.getSaveName();
        Path filePath = uploadPath.resolve(uploadedDate).resolve(filename);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() == false || resource.isFile() == false) {
                throw new RuntimeException(filePath.toString());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException(filePath.toString());
        }
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "id") Integer id) {
        devdragons.yiuServer.domain.Files file = filesRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Resource resource = readFileAsResource(file);

        try {
            String encodedFilename = URLEncoder.encode(file.getOriginName(), "UTF-8").replaceAll("\\+", "%20");

            Path filePath = Paths.get(file.getSaveName());
            String contentType = Files.probeContentType(filePath);
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getSize()))
                    .body(resource);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/show")
    public ResponseEntity<Resource> showFile(@RequestParam(value = "id") Integer fileId) {
        devdragons.yiuServer.domain.Files file = filesRepository.findById(fileId).orElseThrow();
        Resource resource = readFileAsResource(file);

        try {
            // 파일명 인코딩 처리
            String encodedFilename = URLEncoder.encode(file.getOriginName(), "UTF-8").replaceAll("\\+", "%20");

            // 파일 MIME 타입 동적 처리
            Path filePath = Paths.get(file.getSaveName());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFilename + "\"")
                    .body(resource);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Filename encoding failed: " + file.getOriginName(), e);
        } catch (IOException e) {
            throw new RuntimeException("Could not determine file content type", e);
        }
    }
}
