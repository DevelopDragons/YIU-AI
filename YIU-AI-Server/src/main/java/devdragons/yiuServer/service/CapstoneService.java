package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.CapstoneMember;
import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.CapstoneRequestDto;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.CapstoneMemberRepository;
import devdragons.yiuServer.repository.CapstoneRepository;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CapstoneService {
    private final CapstoneMemberRepository capstoneMemberRepository;
    private final CapstoneRepository capstoneRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final FilesRepository filesRepository;

    /*
     * @description 캡스톤 팀 및 멤버 등록
     * @author 김예서
     * @param teamName, thumbnail, professor, topic, description, link, users
     * @return Boolean
     * */
    public Boolean createCapstone(CapstoneRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getTeamName(), requestDto.getProfessor(), requestDto.getTopic(), requestDto.getDescription(), requestDto.getLink(), requestDto.getUsers()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            Capstone capstone = Capstone.builder()
                    .teamName(requestDto.getTeamName())
                    .professor(requestDto.getProfessor())
                    .topic(requestDto.getTopic())
                    .description(requestDto.getDescription())
                    .link(requestDto.getLink())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Capstone savedCapstone = capstoneRepository.save(capstone);

            if(requestDto.getThumbnail() != null) {
                List<FileRequestDto> thumbnails = fileService.uploadFiles(requestDto.getThumbnail());

                fileService.saveFiles(FileType.CAPSTONE, savedCapstone.getId(), "thumbnail", thumbnails);
            }

            List<User> users = requestDto.getUsers();

            for(int i=0; i<users.size(); i++) {
                // 없는 사용자일 경우 에러 발생
                if(!userRepository.existsById(users.get(i).getId())) {
                    throw new CustomException(ErrorCode.NOT_EXIST_ID);
                }

                User user = users.get(i);

                if(capstoneMemberRepository.existsCapstoneMemberByUser(user)) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                }

                int isCaptain = 0;
                if(i == 0) {
                    isCaptain = 1;
                }

                CapstoneMember capstoneMember = CapstoneMember.builder()
                        .capstone(savedCapstone)
                        .user(user)
                        .isCaptain(isCaptain)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                capstoneMemberRepository.save(capstoneMember);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    /*
     * @description 캡스톤 팀 및 멤버 수정
     * @author 김예서
     * @param capstone_id, teamName, thumbnail, professor, topic, description, link, users
     * @return Boolean
     * */
    public Boolean updateCapstone(Integer id, CapstoneRequestDto requestDto) throws Exception {
        Capstone capstone = capstoneRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
        List<User> users = requestDto.getUsers();

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTeamName(), requestDto.getProfessor(), requestDto.getTopic(), requestDto.getDescription(), requestDto.getLink(), requestDto.getUsers()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            capstone.setTeamName(requestDto.getTeamName());
            capstone.setProfessor(requestDto.getProfessor());
            capstone.setTopic(requestDto.getTopic());
            capstone.setDescription(requestDto.getDescription());
            capstone.setLink(requestDto.getLink());
            capstone.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getThumbnail() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.CAPSTONE, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.CAPSTONE, id);

                List<FileRequestDto> thumbnails = fileService.uploadFiles(requestDto.getThumbnail());

                fileService.saveFiles(FileType.CAPSTONE, id, "thumbnail", thumbnails);
            }

            capstoneMemberRepository.deleteAllByCapstone(capstone);


            for(int i=0; i<users.size(); i++) {
                // 없는 사용자일 경우 에러 발생
                if(!userRepository.existsById(users.get(i).getId())) {
                    throw new CustomException(ErrorCode.NOT_EXIST_ID);
                }

                User user = users.get(i);

                if(capstoneMemberRepository.existsCapstoneMemberByUser(user)) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                }

                int isCaptain = 0;
                if(i == 0) {
                    isCaptain = 1;
                }

                CapstoneMember capstoneMember = CapstoneMember.builder()
                        .capstone(capstone)
                        .user(user)
                        .isCaptain(isCaptain)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                capstoneMemberRepository.save(capstoneMember);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 캡스톤 팀 및 멤버 삭제
     * @author 김예서
     * @param capstone_id
     * @return Boolean
     * */
    public Boolean deleteCapstone(Integer id) throws Exception {
        Capstone capstone = capstoneRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            // 팀 유저 삭제
            capstoneMemberRepository.deleteAllByCapstone(capstone);

            // thumbnail 삭제
            if(filesRepository.existsAllByTypeAndTypeId(FileType.CAPSTONE, id)) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.CAPSTONE, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.CAPSTONE, id);
            }

            capstoneRepository.delete(capstone);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
