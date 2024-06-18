package org.example.blog_project.tags;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blog_project.post_tag.PostTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @NotNull
    private String tagName;

    @OneToMany(mappedBy = "tags", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PostTag> postTagList = new ArrayList<>();

}
