package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class InfoRequestDto {
    private String name;
    private String engName;
    private String address;
    private String tel;
    private String mail;
    private String professor;
    private String greeting;
    private List<MultipartFile> bossImage;
    private String title;
    private String contents;
    private List<MultipartFile> image;
    private String slogan;
    private String introduce;
    private List<MultipartFile> introduceImage;
}
