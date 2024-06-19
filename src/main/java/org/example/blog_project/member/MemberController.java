package org.example.blog_project.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blog_project.member.dto.LoginForm;
import org.example.blog_project.member.dto.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("register")
    public String registerForm(@ModelAttribute("registerForm")RegisterForm form){
        return "login/registerForm";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm")LoginForm form){
        return "login/loginForm";
    }

    @PostMapping("/api/register")
    public String register(@Valid @ModelAttribute RegisterForm form,
                           BindingResult bindingResult,
                           HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/registerForm";
        }

        Boolean result = memberService.register(form);
        if(result){
            return "redirect:/login";
        }else {
            bindingResult.reject("registerFail","회원가입 중 문제가 생겼습니다");
            return "login/registerForm";
        }

    }

    @PostMapping("/api/login")
    public String login(@Valid @ModelAttribute LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Long memberId = memberService.login(form);
        if(memberId == null){
            bindingResult.reject("loginFail","ID 또는 비밀번호가 맞지않습니다");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,memberId);

        return "redirect:" + redirectURL;
    }
}
