package org.example.blog_project.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blog_project.member.Member;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String series = null;

    @NotNull
    private Boolean isTemp = true;

    @NotNull
    private Boolean isHide = false;

    @NotNull
    private LocalDate createdAt = LocalDate.now();

    private String mainImageUrl = null;

    @NotNull
    private Integer views;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
