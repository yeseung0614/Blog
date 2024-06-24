package org.example.blog_project.post_tag;

import org.example.blog_project.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag,Long> {
    void deleteAllByPost(Post post);
}
