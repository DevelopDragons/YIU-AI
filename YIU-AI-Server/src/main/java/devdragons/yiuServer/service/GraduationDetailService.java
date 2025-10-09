package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.GraduationDetail;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.UserGraduation;
import devdragons.yiuServer.domain.state.StatusCategory;
import devdragons.yiuServer.dto.request.GraduationDetailRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.GraduationDetailRepository;
import devdragons.yiuServer.repository.UserGraduationRepository;
import devdragons.yiuServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GraduationDetailService {
    private final GraduationDetailRepository graduationDetailRepository;
    private final UserGraduationRepository userGraduationRepository;
    private final UserRepository userRepository;

    /*
     * @description graduationDetail & userGraduation 등록
     * @author 김예서
     * @param year, title, content
     * @return Boolean
     * */
    public Boolean createGraduationDetail(GraduationDetailRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getYear(), requestDto.getTitle(), requestDto.getContent(), requestDto.getGraduationCategory()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            GraduationDetail graduationDetail = GraduationDetail.builder()
                    .year(requestDto.getYear())
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .category(requestDto.getGraduationCategory())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // graduationDetail 생성
            GraduationDetail savedGraduationDetail = graduationDetailRepository.save(graduationDetail);

            // request 로 받아온 year 를 바탕으로 해당 학번의 학생 list 가져오기
            List<User> students = userRepository.findAll().stream()
                    .filter(user -> user.getId() != null && user.getId().startsWith(String.valueOf(requestDto.getYear())))
                    .collect(Collectors.toList());

            // 가져온 학생들의 개별 졸업 요건 생성
            for(User user : students) {
                UserGraduation userGraduation = UserGraduation.builder()
                        .user(user)
                        .graduationDetail(savedGraduationDetail)
                        .status(StatusCategory.NOT_SUBMITTED)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                userGraduationRepository.save(userGraduation);
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description graduationDetail 수정
     * @author 김예서
     * @param id, title, content
     * @return Boolean
     * */
    public Boolean updateGraduationDetail(Integer id, GraduationDetailRequestDto requestDto) throws Exception {
        GraduationDetail graduationDetail = graduationDetailRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getContent()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            graduationDetail.setTitle(requestDto.getTitle());
            graduationDetail.setContent(requestDto.getContent());
            graduationDetail.setUpdatedAt(LocalDateTime.now());
            graduationDetailRepository.save(graduationDetail);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description graduationDetail 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteGraduationDetail(Integer id) throws Exception {
        GraduationDetail graduationDetail = graduationDetailRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        List<UserGraduation> userGraduations = userGraduationRepository.findByGraduationDetail(graduationDetail);

        userGraduationRepository.deleteAll(userGraduations);
        graduationDetailRepository.delete(graduationDetail);

        return true;
    }

    /*
     * @description graduationDetail 조회
     * @author 김예서
     * @param year
     * @return List<GraduationDetail>
     * */
    public List<GraduationDetail> getGraduationDetailByYear(Integer year) throws Exception {
        List<GraduationDetail> graduationDetails = graduationDetailRepository.findAllByYear(year);

        ArrayList<GraduationDetail> graduationDetailArrayList = new ArrayList<>(graduationDetails);

        return graduationDetailArrayList;
    }
}
