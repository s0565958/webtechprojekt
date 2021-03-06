package de.htwberlin.webtech.web;

import de.htwberlin.webtech.service.PostService;
import de.htwberlin.webtech.web.api.Post;
import de.htwberlin.webtech.web.api.PostManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/api/v1/posts")
    public ResponseEntity<List<Post>> fetchPosts(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping(path = "/api/v1/posts/{id}")
    public ResponseEntity<Post> fetchPersonById(@PathVariable Long id){
        var post = postService.findById(id);
        return post != null? ResponseEntity.ok(post) : ResponseEntity.notFound().build();

    }

    @PostMapping(path = "/api/v1/posts")
    public ResponseEntity<Void> createpost(@Valid @RequestBody PostManipulationRequest request) throws URISyntaxException {
            var post = postService.create(request);
            URI uri = new URI("/api/v1/posts/" + post.getId());
            return ResponseEntity.created(uri).header("Access-Control-Expose-Headers","Location").build();
    }

    @PutMapping(path = "/api/v1/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostManipulationRequest request){
        var post = postService.update(id, request);
        return post != null? ResponseEntity.ok(post) : ResponseEntity.notFound().build();

    }

    @DeleteMapping(path = "/api/v1/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean successful = postService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
