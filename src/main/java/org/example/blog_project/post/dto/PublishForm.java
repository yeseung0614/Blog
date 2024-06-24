package org.example.blog_project.post.dto;

import lombok.Data;

@Data
public class PublishForm {
    private String introduce;
    private Boolean isHide;
    private String series;
    private String postUrl;
}
