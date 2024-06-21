package org.example.blog_project.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostResDto {
    private Long postId;
    private Boolean isTemp;
}
