package org.example.blog_project.member;


import lombok.RequiredArgsConstructor;
import org.example.blog_project.member.dto.InfoDto;
import org.example.blog_project.member.dto.LoginForm;
import org.example.blog_project.member.dto.RegisterForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final MemberRepository memberRepository;


    public Long login(LoginForm form){
        Member member = memberRepository.findMemberByLoginId(form.getLoginId());

        if(member == null){
            return null;
        }

        if(!member.getPassword().equals(form.getPassword())){
            return null;
        }

        return member.getMemberId();
    }

    public Boolean register(RegisterForm form) {
        // 아이디와 이메일 형식 검증
        if (!form.getLoginId().matches("^[a-zA-Z0-9]{8,}$")) {
            return false;
        }
        if (!form.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return false;
        }
        if (!idCheck(form.getLoginId()) || !emailCheck(form.getEmail())) {
            return false;
        }

        Member member = Member.builder()
                .loginId(form.getLoginId())
                .password(form.getPassword())
                .name(form.getName())
                .email(form.getEmail())
                .blogName(form.getLoginId())
                .allowCommentPush(form.getAllowCommentPush())
                .allowUpdatePush((form.getAllowUpdatePush()))
                .build();

        memberRepository.save(member);
        return true;
    }


    public Boolean idCheck(String loginId){
        Member result = memberRepository.findMemberByLoginId(loginId);
        if(result == null){
            return true;
        }
        return false;
    }

    public Boolean emailCheck(String email){
        Member result = memberRepository.findMemberByEmail(email);
        if(result == null){
            return true;
        }
        return false;
    }

    public InfoDto getUserInfo(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 유저"));
        return InfoDto.builder()
                .blogName(member.getBlogName())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .email(member.getEmail())
                .allowCommentPush(member.getAllowCommentPush())
                .allowUpdatePush(member.getAllowUpdatePush())
                .build();
    }

    public void updateBlogName(String blogName,Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 유저"));
        member.setBlogName(blogName);

        memberRepository.save(member);

    }

    public String updateProfileImage(MultipartFile file, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 유저"));
        String path = saveFile(file);
        member.setProfileImageUrl(path);

        memberRepository.save(member);

        return path;
    }

    private String saveFile(MultipartFile file) {
        try {
            // Ensure the upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            Path filePath = uploadPath.resolve(uniqueFilename);

            // Save the file
            file.transferTo(filePath.toFile());

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    public Boolean deleteMember(Long memberId,String password){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));
        if (member == null || !(member.getPassword().equals(password))){
            return false;
        }
        memberRepository.delete(member);
        return true;
    }
}
