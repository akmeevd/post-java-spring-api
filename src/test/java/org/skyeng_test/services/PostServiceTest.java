package org.skyeng_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyeng_test.models.Post;
import org.skyeng_test.repositories.PostRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private final Post post = new Post();

    @BeforeEach
    public void setup() {
        post.setIndex(350000);
    }

    @Test
    public void create() {
        Mockito.doReturn(post).when(postRepository).save(any());
        Post actualPost = postService.create(post);
        Assertions.assertEquals(post, actualPost);
    }

    @Test
    public void findByIndex() {
        Mockito.doReturn(Optional.of(post)).when(postRepository).findByIndex(anyInt());
        Post actualPost = postService.findByIndex(post.getIndex());
        Assertions.assertEquals(post, actualPost);
    }


}
