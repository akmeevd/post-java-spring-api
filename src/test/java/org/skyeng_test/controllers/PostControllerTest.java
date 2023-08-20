package org.skyeng_test.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyeng_test.models.Post;
import org.skyeng_test.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    private final JSONObject postJson = new JSONObject();
    private final Post post = new Post();

    @BeforeEach
    public void setup() throws JSONException {
        post.setIndex(35000);
        postJson.put("index", post.getIndex());
    }

    @Test
    public void create() throws Exception {
        Mockito.doReturn(post).when(postService).create(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .content(postJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value(post.getIndex()));
    }
}
