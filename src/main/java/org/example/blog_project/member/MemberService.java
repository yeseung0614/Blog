package org.example.blog_project.member;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.member.dto.LoginForm;
import org.example.blog_project.member.dto.RegisterForm;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
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

    public Boolean register(RegisterForm form){
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
}
