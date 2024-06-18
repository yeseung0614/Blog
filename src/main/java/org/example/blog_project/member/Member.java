package org.example.blog_project.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.blog_project.comment.Comment;
import org.example.blog_project.follow.Follow;
import org.example.blog_project.likes.Likes;
import org.example.blog_project.post.Post;
import org.example.blog_project.read_post.ReadPost;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String profileImageUrl;

    @NotNull
    private LocalDate registrationDate = LocalDate.now();

    @NotNull
    private Boolean isAdmin = false;

    @NotNull
    private String blogName = loginId;

    @NotNull
    private Boolean allowCommentPush = true;

    @NotNull
    private Boolean allowUpdatePush = true;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ReadPost> readPostList = new ArrayList<>();
}
