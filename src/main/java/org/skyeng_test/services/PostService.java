package org.skyeng_test.services;


import lombok.extern.slf4j.Slf4j;
import org.skyeng_test.models.Post;
import org.skyeng_test.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * service for working to posts
 */
@Service
public class PostService {
    private final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * method for creating posts and adding them to db
     * uses {@link PostRepository#save(Object)}
     * @param post
     * @return Post
     */
    public Post create(Post post) {
        logger.info("creating post");
        return postRepository.save(post);
    }

    /**
     * method is looking for posts by index
     * uses {@link PostRepository#findByIndex(int)}
     * @param index
     * @return Post
     * @throws EntityNotFoundException
     */
    public Post findByIndex(int index) {
        logger.info("finding post by index {}", index);
        Optional<Post> post = Optional.ofNullable(postRepository.findByIndex(index))
                .orElseThrow(() -> new EntityNotFoundException("post with index: " + index + "doesn't exist"));
        return post.get();
    }
}
