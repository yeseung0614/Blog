package org.example.blog_project.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags,Long> {
    Tags findByTagName(String tagName);
}
