package org.example.blog_project.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoDto {
    private String blogName;
    private String name;
    private String profileImageUrl;
    private String email;
    private boolean allowCommentPush;
    private boolean allowUpdatePush;
}
