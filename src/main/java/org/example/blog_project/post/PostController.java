package org.example.blog_project.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.member.UserContext;
import org.example.blog_project.post.dto.PostForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;


    @GetMapping("/postform")
    public String postForm(){
        return "/post/createPostForm";
    }

    @PostMapping("/api/posts")
    public String createPost(@ModelAttribute PostForm postForm,
                             @RequestParam(name = "files",required = false) List<MultipartFile> files){
        Long memberId = Long.parseLong(UserContext.getUserId());
        postService.createPost(memberId,postForm,files);
        return "redirect:/";
    }
}
