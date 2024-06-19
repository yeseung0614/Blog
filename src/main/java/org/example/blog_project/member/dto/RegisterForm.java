package org.example.blog_project.member.dto;

import lombok.Data;

@Data
public class RegisterForm {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private Boolean allowCommentPush;
    private Boolean allowUpdatePush;
}
