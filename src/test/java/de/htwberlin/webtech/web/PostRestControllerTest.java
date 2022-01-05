package de.htwberlin.webtech.web;

import de.htwberlin.webtech.service.PostService;
import de.htwberlin.webtech.web.api.Post;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("should return found post from post service")
    void should_return_found_post_from_post_service() throws Exception {
        // given
        var posts = List.of(
                new Post(1, "Messi", "Sport", "JohnJH", "Messi ist mein Idol"),
                new Post(2, "Samsung", "Technology", "FredFR", "Was ist das beste Samsung Handy")
        );
        doReturn(posts).when(postService).findAll();

        // when
        mockMvc.perform(get("/api/v1/posts"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Messi"))
                .andExpect(jsonPath("$[0].content").value("Sport"))
                .andExpect(jsonPath("$[0].username").value("JohnJH"))
                .andExpect(jsonPath("$[0].body").value("Messi ist mein Idol"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Samsung"))
                .andExpect(jsonPath("$[1].content").value("Technology"))
                .andExpect(jsonPath("$[1].username").value("FredFR"))
                .andExpect(jsonPath("$[1].body").value("Was ist das beste Samsung Handy"));
    }

    @Test
    @DisplayName("should return 404 if post is not found")
    void should_return_404_if_post_is_not_found() throws Exception {
        // given
        doReturn(null).when(postService).findById(anyLong());

        // when
        mockMvc.perform(get("/api/v1/posts/123"))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 201 http status and Location header when creating a post")
    void should_return_201_http_status_and_location_header_when_creating_a_post() throws Exception {
        // given
        String postToCreateAsJson = "{\"title\": \"Messi\", \"content\":\"Sport\", \"username\":\"JohnJH\", \"body\":\"Was ist das beste Samsung Handy\"}";
        var posts = new Post(123, null, null, null, null);
        doReturn(posts).when(postService).create(any());

        // when
        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postToCreateAsJson)
                )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.equalTo(("/api/v1/posts/" + posts.getId()))));
//            .andExpect(header().string("Location", Matchers.containsString(Long.toString(posts.getId()))));

    }

    @Test
    @DisplayName("should validate create post request")
    void should_validate_create_post_request() throws Exception {
        // given
        String postToCreateAsJson = "{\"title\": \"\", \"content\":\"\", \"username\":\"JohnJH\", \"body\":\"a\"}";

        // when
        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postToCreateAsJson)
                )
                // then
                .andExpect(status().isBadRequest());
    }
}

