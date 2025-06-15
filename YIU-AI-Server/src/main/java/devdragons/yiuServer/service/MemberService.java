package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Member;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.domain.state.RequiredCategory;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.MemberRequestDto;
import devdragons.yiuServer.dto.response.MemberResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

//    private void validateRequiredFields(List<Object> fields) {
//        Predicate<Object> isNullOrEmpty = field ->
//                field == null || (field instanceof String && ((String) field).isEmpty());
//
//        if(fields.stream().anyMatch(isNullOrEmpty)) {
//            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
//        }
//    }

    /*
     * @description member 등록
     * @author 김예서
     * @param name, mail, tel, labName, labLink, labCategory, type, role, description, image, labImage, required
     * @return Boolean
     * */
    public Boolean createMember(MemberRequestDto requestDto) throws Exception {
        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getMail(), requestDto.getTel(), requestDto.getType(), requestDto.getRole(), requestDto.getRequired()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            Member member = Member.builder()
                    .name(requestDto.getName())
                    .mail(requestDto.getMail())
                    .tel(requestDto.getTel())
                    .labName(requestDto.getLabName())
                    .labLink(requestDto.getLabLink())
                    .labCategory(requestDto.getLabCategory())
                    .type(requestDto.getType())
                    .role(requestDto.getRole())
                    .required(requestDto.getRequired())
                    .description(requestDto.getDescription())
                    .build();

            Member savedMember = memberRepository.save(member);

            if(requestDto.getImage() != null || requestDto.getLabImage() != null) {
                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                List<FileRequestDto> labImage = fileService.uploadFiles(requestDto.getLabImage());

                fileService.saveFiles(FileType.MEMBER, savedMember.getId(), "image", image);
                fileService.saveFiles(FileType.MEMBER, savedMember.getId(), "labImage", labImage);
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description member 수정
     * @author 김예서
     * @param id, name, mail, tel, labName, labLink, labCategory, type, role, description, image, labImage
     * @return Boolean
     * */
    public Boolean updateMember(Integer id, MemberRequestDto requestDto) throws Exception {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        List<Object> requiredFields = Arrays.asList(
                requestDto.getName(), requestDto.getMail(), requestDto.getTel(), requestDto.getType(), requestDto.getRole(), requestDto.getRequired()
        );

        CommonService.validateRequiredFields(requiredFields);

        try {
            member.setName(requestDto.getName());
            member.setMail(requestDto.getMail());
            member.setTel(requestDto.getTel());
            member.setLabName(requestDto.getLabName());
            member.setLabLink(requestDto.getLabLink());
            member.setLabCategory(requestDto.getLabCategory());
            member.setType(requestDto.getType());
            member.setRole(requestDto.getRole());
            member.setRequired(requestDto.getRequired());
            member.setDescription(requestDto.getDescription());

            if(requestDto.getImage() != null || requestDto.getLabImage() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.MEMBER, id);
                fileService.deleteFiles(deleteFiles);

                filesRepository.deleteAllByTypeAndTypeId(FileType.MEMBER, id);

                List<FileRequestDto> image = fileService.uploadFiles(requestDto.getImage());
                List<FileRequestDto> labImage = fileService.uploadFiles(requestDto.getLabImage());

                fileService.saveFiles(FileType.MEMBER, id, "image", image);
                fileService.saveFiles(FileType.MEMBER, id, "labImage", labImage);
            }
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description member 삭제
     * @author 김예서
     * @param id
     * @return Boolean
     * */
    public Boolean deleteMember(Integer id) throws Exception {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.MEMBER, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.MEMBER, id);

            memberRepository.deleteById(member.getId());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description member 조회
     * @author 김예서
     * @return List<MemberResponseDto>
     * */
    public List<MemberResponseDto> getMember() throws Exception {
        List<Member> members = memberRepository.findAll();
        List<Files> images = filesRepository.findAllByTypeAndCategory(FileType.MEMBER, "image");
        List<Files> labImages = filesRepository.findAllByTypeAndCategory(FileType.MEMBER, "labImage");

        List<MemberResponseDto> getListDto = new ArrayList<>();
        for(Member member : members) {
            List<Files> filteredImages = images.stream()
                    .filter(files -> files.getTypeId().equals(member.getId()))
                    .collect(Collectors.toList());
            List<Files> filteredLabImages = labImages.stream()
                    .filter(files -> files.getTypeId().equals(member.getId()))
                    .collect(Collectors.toList());
            getListDto.add(MemberResponseDto.GetMemberDto(member, filteredImages, filteredLabImages));
        }

        return getListDto;
    }
}
