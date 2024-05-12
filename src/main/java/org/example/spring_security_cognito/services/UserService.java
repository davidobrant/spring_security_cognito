package org.example.spring_security_cognito.services;

import org.example.spring_security_cognito.models.Post;
import org.example.spring_security_cognito.models.User;
import org.example.spring_security_cognito.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    public List<User> getUsers() { return (List<User>) userRepository.findAll(); }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getAuthUser(OAuth2AuthenticationToken token) {
        var id = Objects.requireNonNull(token.getPrincipal().getAttribute("sub")).toString();
        return getUserById(id);
    }

    public User saveUser(User user) {
        var existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) return userRepository.save(user);
        return existingUser;
    }

    public boolean deleteUser(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            postService.deletePostsByUser(userId);
            userRepository.deleteById(userId);
            return true;
        }

        return false;
    }

}
