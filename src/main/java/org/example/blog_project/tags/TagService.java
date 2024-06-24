package org.example.blog_project.tags;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Long createOrGetExistingTag(String tagName){
        Tags tag = tagRepository.findByTagName(tagName);
        if(tag!=null){
            return tag.getTagId();
        }else {
            Tags newTag = new Tags(tagName);
            Tags savedTag = tagRepository.save(newTag);
            return savedTag.getTagId();
        }
    }
}
