package org.example.blog_project.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.member.UserContext;
import org.example.blog_project.post.dto.CreatePostResDto;
import org.example.blog_project.post.dto.PostForm;
import org.example.blog_project.post.dto.PublishForm;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public CreatePostResDto createPost(@RequestPart(name = "postForm") PostForm postForm,
                             @RequestParam(name = "files",required = false) List<MultipartFile> files){
       log.info(postForm.getTitle());
       log.info(""+postForm.getTags()[0]);
       log.info(postForm.getContent());
       log.info(""+postForm.getIsTemp());
       log.info(""+files);

        Long memberId = Long.parseLong(UserContext.getUserId());
        CreatePostResDto result = postService.createPost(memberId, postForm, files);

        log.info("" + result.getPostId() + result.getIsTemp());
        if (!result.getIsTemp()) {
            return result;
        }

        return null;
    }

    @PostMapping("/api/posts/publish")
    public String publishPost(@RequestPart(name = "publishForm")PublishForm form,
                              @RequestParam(name = "file",required = false) MultipartFile file) {

        log.info(""+form.getPostId());
        log.info(""+form.getIntroduce());
        log.info(""+form.getIsHide());
        log.info(""+form.getSeries());
        log.info(""+form.getPostUrl());

        String url = postService.publishPost(form, file);
        return url;
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostForm form, @PathVariable Long postId ){
        String result = postService.updatePost(form, postId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        String result = postService.deletePost(postId);
        return ResponseEntity.ok(result);
    }




}
