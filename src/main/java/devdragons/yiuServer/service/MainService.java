package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.dto.request.UserRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    private int authNum;

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
            System.out.println(e.getMessage());
            System.out.println(ErrorCode.INTERNAL_SERVER_ERROR);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 회원가입 시 이메일 인증
     * @author 김예서
     * @param id
     * @return
     * */
    public int sendEmailWhenRegister(String id) throws MessagingException, UnsupportedEncodingException {
        // 데이터 미입력
        if(id.isEmpty()) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        // 회원 중복
        if(userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.DUPLICATE);
        }

        // 메일 전송에 필요한 정보 설정(@yiu.ac.kr)
        MimeMessage emailForm = createEmailForm(id + "@yiu.ac.kr");

        // 메일 전송
        javaMailSender.send(emailForm);

        return authNum;
    }

    /*
     * @description 메일 양식 작성
     * @author 김예서
     * @param email
     * @return
     * */
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        // 코드를 생성합니다.
        createCode();
        String setFrom = "yiuaiservicelab@gmail.com";	// 보내는 사람
        String toEmail = email;
        String title = "AI 학부 홈페이지 회원가입 인증번호";		// 메일 제목

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);	// 받는 사람 설정
        message.setSubject(title);		// 제목 설정

        // 메일 내용 설정
        String msgOfEmail="";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 용인대학교 AI 학부 홈페이지 입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 입력해주세요<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authNum + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        // 보내는 사람 설정
        message.setFrom(setFrom);
        // 위 String으로 받은 내용을 아래에 넣어 내용 설정
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    /*
     * @description 6자리 숫자로 이루어진 인증코드를 생성
     * @author 김예서
     * @param id
     * @return
     * */
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0; i<6; i++)
            key.append(random.nextInt(9));

        authNum = Integer.parseInt(key.toString());
    }
}
