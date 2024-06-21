package org.example.blog_project.post_image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog_project.post.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostImageService {
    private final PostImageRepository postImageRepository;

    public void createPostImage(Post post, List<MultipartFile> files){
        try{
            String[] postImageUrls = saveImages(files);
            for (int i = 0;i < files.size();i++){
                PostImage postImage = PostImage.builder()
                        .imageUrl(postImageUrls[i])
                        .orderIndex(i)
                        .post(post)
                        .build();
                postImageRepository.save(postImage);
                post.getPostImageList().add(postImage);
            }


        } catch (IOException e){
            log.info(e.getMessage());
        }
    }

    @Value("${content.file.path}")
    private String contentFilePath;

    private String[] saveImages(List<MultipartFile> files) throws IOException {
        String[] savedFilePaths = new String[files.size()];

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString() + fileExtension;
            String savedFilePath = contentFilePath + File.separator + savedFileName;

            File savedFile = new File(savedFilePath);
            file.transferTo(savedFile);

            savedFilePaths[i] = "/content_images/" + savedFileName; // URL path
        }

        return savedFilePaths;
    }
}
