package org.example.blog_project.post_tag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blog_project.post.Post;
import org.example.blog_project.tags.Tags;

@Entity
@NoArgsConstructor
@Getter
public class PostTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tags tags;

    public PostTag(Post post, Tags tags) {
        this.post = post;
        this.tags = tags;
    }
}
