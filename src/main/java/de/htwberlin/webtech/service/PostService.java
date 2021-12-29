package de.htwberlin.webtech.service;

import de.htwberlin.webtech.persistence.PostEntity;
import de.htwberlin.webtech.persistence.PostRepository;
import de.htwberlin.webtech.web.api.PostManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<de.htwberlin.webtech.web.api.Post> findAll(){
        List<PostEntity> posts = postRepository.findAll();
        return posts.stream()
                .map(this::transformEntity)
                .collect(Collectors.toList());
    }

    public de.htwberlin.webtech.web.api.Post findById(Long id){
        var postEntity = postRepository.findById(id);
        return postEntity.map(this::transformEntity).orElse(null);

    }

    public de.htwberlin.webtech.web.api.Post create(PostManipulationRequest request){
        var postEntity = new PostEntity(request.getTitle(), request.getContent(), request.getUsername(), request.getBody());
        postEntity = postRepository.save(postEntity);
        return transformEntity(postEntity);
    }

    public de.htwberlin.webtech.web.api.Post update(Long id, PostManipulationRequest request){
        var postEntityOptional = postRepository.findById(id);
        if (postEntityOptional.isEmpty()){
            return null;
        }

        var postEntity = postEntityOptional.get();
        postEntity.setTitle(request.getTitle());
        postEntity.setContent(request.getContent());
        postEntity.setUsername(request.getUsername());
        postEntity.setBody(request.getBody());
        postEntity = postRepository.save(postEntity);

        return transformEntity(postEntity);
    }

    public boolean deleteById(Long id){
        if (!postRepository.existsById(id)){
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

    private de.htwberlin.webtech.web.api.Post transformEntity(PostEntity postEntity){
        return new de.htwberlin.webtech.web.api.Post(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getUsername(),
                postEntity.getBody()
        );

    }
}
