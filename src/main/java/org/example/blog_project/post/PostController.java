package org.example.blog_project.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.member.UserContext;
import org.example.blog_project.post.dto.CreatePostResDto;
import org.example.blog_project.post.dto.PostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/publishForm")
    public String publishForm(@RequestParam("postId") Long postId, Model model) {
        model.addAttribute("postId", postId);
        return "/post/publishForm";
    }

    @PostMapping("/api/posts")
    public String createPost(@RequestPart PostForm postForm,
                             @RequestParam(name = "files",required = false) List<MultipartFile> files){
        Long memberId = Long.parseLong(UserContext.getUserId());
        CreatePostResDto result = postService.createPost(memberId, postForm, files);

        log.info("" + result.getPostId() + result.getIsTemp());
        if (!result.getIsTemp()) {
            return "redirect:/publishForm?postId=" + result.getPostId();
        }

        return "redirect:/";
    }

    @PostMapping("/api/posts/publish")
    public String publishPost(@RequestParam("postId") Long postId) {
        postService.publishPost(postId);
        return "redirect:/";
    }


}
