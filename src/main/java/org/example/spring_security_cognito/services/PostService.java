package org.example.spring_security_cognito.services;

import org.example.spring_security_cognito.models.Post;
import org.example.spring_security_cognito.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByUpdatedAtDesc();
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public boolean deletePostById(Long id) {
        try {
            System.out.println(id);
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void deletePostsByUser(String userId) {
        for (var post : getPostsByUserId(userId)) {
            postRepository.delete(post);
        }
    }
}
