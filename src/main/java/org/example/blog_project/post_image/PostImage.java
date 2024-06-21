package org.example.blog_project.post_image;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blog_project.post.Post;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PostImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @NotNull
    private String imageUrl;

    @NotNull
    private int orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostImage(String imageUrl, int orderIndex, Post post) {
        this.imageUrl = imageUrl;
        this.orderIndex = orderIndex;
        this.post = post;
    }
}
