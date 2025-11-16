package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CapstoneMemberRequestDto {
    private Capstone id;
    private User captain;
    private User member1;
    private User member2;
    private User member3;
    private User member4;
    private User member5;
    private User member6;
    private User member7;
    private User member8;
    private User member9;
    private User member10;
}
