package com.example.blog.controller.api;

import com.example.blog.dto.PostUpdateDTO;
import com.example.blog.mapper.PostMapper;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private Faker faker;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testUser = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .create();

        testPost = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getName), () -> faker.gameOfThrones().house())
                .supply(Select.field(Post::getBody), () -> faker.gameOfThrones().quote())
                .supply(Select.field(Post::getSlug), () -> faker.internet().slug())
                .supply(Select.field(Post::getAuthor), () -> testUser)
                .create();
    }

    @Test
    void testIndex() throws Exception {
        postRepository.save(testPost);

        var result = mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    void testCreate() throws Exception {
        // Сохраняем testPost (вместе с testUser каскадом), чтобы ReferenceMapper нашёл автора
        postRepository.save(testPost);

        var dto = postMapper.map(testPost);
        // Меняем slug на уникальный, чтобы не нарушить unique constraint
        dto.setSlug("new-unique-slug-" + System.currentTimeMillis());

        var request = post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var post = postRepository.findBySlug(dto.getSlug()).orElseThrow();
        assertNotNull(post);
    }

    @Test
    void testUpdate() throws Exception {
        postRepository.save(testPost);

        var dto = new PostUpdateDTO();
        dto.setName(JsonNullable.of("new name"));
        dto.setSlug(JsonNullable.of(testPost.getSlug()));
        dto.setBody(JsonNullable.of(testPost.getBody()));
        dto.setAuthorId(JsonNullable.of(testUser.getId()));

        var request = put("/api/posts/" + testPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var post = postRepository.findById(testPost.getId()).orElseThrow();
        assertThat(post.getName()).isEqualTo("new name");
    }

    @Test
    void testShow() throws Exception {
        postRepository.save(testPost);

        var request = get("/api/posts/" + testPost.getId());

        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("slug").isEqualTo(testPost.getSlug()),
                v -> v.node("name").isEqualTo(testPost.getName()),
                v -> v.node("body").isEqualTo(testPost.getBody())
        );
    }

    @Test
    void testDestroy() throws Exception {
        postRepository.save(testPost);

        var request = delete("/api/posts/" + testPost.getId());

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(postRepository.existsById(testPost.getId())).isFalse();
    }
}