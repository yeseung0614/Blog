package org.example.blog_project.post.dto;

import lombok.Data;
import org.example.blog_project.post_tag.PostTag;

import java.util.List;

@Data
public class PostForm {
    private String title;
    private List<PostTag> tags;
    private String content;
    private Boolean isTemp;
}
