package org.example.blog_project.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.member.Member;
import org.example.blog_project.member.MemberRepository;
import org.example.blog_project.post.dto.CreatePostResDto;
import org.example.blog_project.post.dto.PostForm;
import org.example.blog_project.post_image.PostImageService;
import org.example.blog_project.post_tag.PostTagService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImageService postImageService;
    private final PostTagService postTagService;

    public CreatePostResDto createPost(Long memberId, PostForm postForm, List<MultipartFile> files){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));
        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .isTemp(postForm.getIsTemp())
                .member(member)
                .build();
        Post savedPost = postRepository.save(post);

        if(postForm.getTags()!=null){
            postTagService.createPostTag(savedPost,postForm.getTags());
        }


        // files가 null일 경우 빈 리스트로 초기화
        if (files == null) {
            files = Collections.emptyList();
        }

        postImageService.createPostImage(post,files);

        log.info("Post created with ID: " + post.getPostId() + ", isTemp: " + post.getIsTemp());


        return CreatePostResDto.builder()
                .postId(post.getPostId())
                .isTemp(post.getIsTemp())
                .build();
    }

    public void publishPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글"));
        post.setTemp(false);
        postRepository.save(post);
    }
}
