package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.UserGraduation;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.domain.state.StatusCategory;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.UserGraduationRequestDto;
import devdragons.yiuServer.dto.response.UserGraduationResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.UserGraduationRepository;
import devdragons.yiuServer.repository.UserRepository;
import devdragons.yiuServer.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserGraduationService {
    private final UserGraduationRepository userGraduationRepository;
    private final UserRepository userRepository;
    private final FilesRepository filesRepository;
    private final FileService fileService;

    /*
     * @description 학생별 졸업 요건 조회
     * @author 김예서
     * @param userId
     * @return List<UserGraduation>
     * */
    public List<UserGraduationResponseDto> getUserGraduation(User userId) throws Exception {
        User user = userRepository.findById(userId.getId()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));
        List<UserGraduation> userGraduations = userGraduationRepository.findAllByUser(user);

        List<UserGraduationResponseDto> getListDto = new ArrayList<>();
        for(UserGraduation userGraduation : userGraduations) {
            getListDto.add(UserGraduationResponseDto.GetUserGraduationDto(userGraduation));
        }
        return getListDto;
    }

    /*
     * @description 졸업 요건 제출
     * @author 김예서
     * @param id, description, file
     * @return Boolean
     * */
    public Boolean submitGraduation(CustomUserDetails userDetails, Integer id, UserGraduationRequestDto requestDto) throws Exception {
        String student = userDetails.getUser().getId();

        UserGraduation userGraduation = userGraduationRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        if(!userGraduation.getUser().getId().equals(student)) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        if(requestDto.getDescription().isEmpty()) new CustomException(ErrorCode.INSUFFICIENT_DATA);

        try {
            userGraduation.setDescription(requestDto.getDescription());
            userGraduation.setStatus(StatusCategory.SUBMITTED);
            userGraduation.setUpdatedAt(LocalDateTime.now());
            UserGraduation savedUserGraduation = userGraduationRepository.save(userGraduation);

            if(requestDto.getFile() != null) {
                List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());

                fileService.saveFiles(FileType.USERGRADUATION, savedUserGraduation.getId(), "file", file);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 졸업 요건 승인 & 반려
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean passGraduation(Integer id, UserGraduationRequestDto requestDto) throws Exception {
        UserGraduation userGraduation = userGraduationRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            userGraduation.setFeedback(requestDto.getFeedback());
            userGraduation.setStatus(requestDto.getStatus());
            userGraduation.setUpdatedAt(LocalDateTime.now());

            userGraduationRepository.save(userGraduation);
            return true;
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
