package org.example.blog_project.post_tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.post.Post;
import org.example.blog_project.post.dto.PostForm;
import org.example.blog_project.tags.TagRepository;
import org.example.blog_project.tags.TagService;
import org.example.blog_project.tags.Tags;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PostTagService {
    private final PostTagRepository postTagRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;

    @Transactional
    public void createPostTag(Post post, String[] tags){
        for (String tagName : tags) {
            Long tagId = tagService.createOrGetExistingTag(tagName);

            Tags tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("존재하지않는 태그"));

            PostTag postTag = new PostTag(post, tag);
            postTagRepository.save(postTag);

            post.getPostTagList().add(postTag);
        }
    }

    @Transactional
    public void deletePostTag(Post post){
        postTagRepository.deleteAllByPost(post);
    }

}
