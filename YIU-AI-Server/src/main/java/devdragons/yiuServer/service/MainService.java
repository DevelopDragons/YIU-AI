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
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    private String authNum;

    private long expRefreshToken = Duration.ofDays(14).toMillis();

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
    public String sendEmailWhenRegister(String id) throws MessagingException, UnsupportedEncodingException {
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
     * @description 비밀번호 변경 시 이메일 인증
     * @author 김예서
     * @param id
     * @return
     * */
    public String sendEmailWhenPwdChange(String id) throws MessagingException, UnsupportedEncodingException {
        // 데이터 미입력
        if(id.isEmpty()) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        // 회원 미존재
        if(!userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_EXIST_MEMBER);
        }

        // 메일 전송에 필요한 정보 설정(@yiu.ac.kr)
        MimeMessage emailForm = createEmailForm(id + "@yiu.ac.kr");

        // 메일 전송
        javaMailSender.send(emailForm);

        return authNum;
    }

    /*
    * @description 비밀번호 변경
    * @author 김예서
    * @param pwd
    * @return
    *  */
    public Boolean changePwd(UserRequestDto requestDto) throws Exception {
        // 데이터 미입력
        if(requestDto.getPwd().isEmpty()) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        User user = userRepository.findById(requestDto.getId()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        try {
            user.setPwd(passwordEncoder.encode(requestDto.getPwd()));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    * @description 로그인
    * @author 김예서
    * @param id, pwd
    * @return
     */
    public LoginDto login(UserRequestDto requestDto) throws Exception {
        // 데이터 미입력
        if(requestDto.getPwd().isEmpty() || requestDto.getId().isEmpty()) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        // 회원 정보 없음
        User user = userRepository.findById(requestDto.getId()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_MEMBER)
        );

        // 회원정보 불일치
        if(!passwordEncoder.matches(requestDto.getPwd(), user.getPwd())) {
            throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
        }

        try {
            String accessToken = jwtProvider.createToken(user);
            user.setRefreshToken(createRefreshToken(user));
            LoginDto response = LoginDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .grade(user.getGrade())
                    .role(user.getRole())
                    .status(user.getStatus())
                    .major(user.getMajor())
                    .department(user.getDepartment())
                    .track(user.getTrack())
                    .entrance(user.getEntrance())
                    .entrance(user.getEntrance())
                    .professor(user.getProfessor())
                    .token(TokenDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(user.getRefreshToken())
                            .build())
                    .build();

            return response;
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description accessToken 재발급
     * @author 김예서
     * @param accessToken, refreshToken
     * @return
     * */
    public TokenDto getNewTokens(TokenDto requestDto) throws Exception {
        String id = null;
        try {
            id = jwtProvider.getId(requestDto.getAccessToken());
        } catch (ExpiredJwtException e) {
            id = e.getClaims().get("id", String.class);
        }

        User user = userRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        Token refreshToken = validRefreshToken(user, requestDto.getRefreshToken());

        try {
            if(refreshToken != null) {
                return TokenDto.builder()
                        .accessToken(jwtProvider.createToken(user))
                        .refreshToken(refreshToken.getRefreshToken())
                        .build();
            } else {
                throw new CustomException(ErrorCode.LOGIN_REQUIRED);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
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

        authNum = key.toString();
    }

    public String createRefreshToken(User user) {
        Token token = tokenRepository.save(
                Token.builder()
                        .id(user.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(expRefreshToken)
                        .build()
        );
        return token.getRefreshToken();
    }

    public Token validRefreshToken(User user, String refreshToken) throws Exception {
        Token token = tokenRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_REQUIRED));
        // 해당유저의 Refresh 토큰 만료 : Redis에 해당 유저의 토큰이 존재하지 않음
        if (token.getRefreshToken() == null) throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        try {
            // 리프레시 토큰 만료일자가 얼마 남지 않았을 때 만료시간 연장
            if (token.getExpiration() < 10) {
                token.setExpiration(1000L);
                tokenRepository.save(token);
            }
            // 토큰이 같은지 비교
            if (!token.getRefreshToken().equals(refreshToken)) {
                // 원래 null
                throw new CustomException(ErrorCode.LOGIN_REQUIRED);
            } else {
                return token;
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
