package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Token;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.dto.LoginDto;
import devdragons.yiuServer.dto.TokenDto;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.TokenRepository;
import devdragons.yiuServer.repository.UserRepository;
import devdragons.yiuServer.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    /*
    * @description 회원가입
    * @author 김예서
    * @param id, name, pwd, grade, role, status, major, department, track, entrance, professor
    * @return
    * */
    public Boolean register(UserRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getId(), requestDto.getPwd(), requestDto.getName(),
                requestDto.getGrade(), requestDto.getRole(), requestDto.getStatus(), requestDto.getMajor()
        );

        // 데이터 미입력
        if (requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 데이터 중복(id)
        if(userRepository.existsById(requestDto.getId())) {
            throw new CustomException(ErrorCode.DUPLICATE);
        }

        try {
            User user = User.builder()
                    .id(requestDto.getId())
                    .name(requestDto.getName())
                    .pwd(passwordEncoder.encode(requestDto.getPwd()))
                    .grade(requestDto.getGrade())
                    .role(requestDto.getRole())
                    .status(requestDto.getStatus())
                    .major(requestDto.getMajor())
                    .department(requestDto.getDepartment())
                    .track(requestDto.getTrack())
                    .entrance(requestDto.getEntrance())
                    .professor(requestDto.getProfessor())
                    .build();

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
