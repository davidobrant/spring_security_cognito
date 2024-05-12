package org.example.spring_security_cognito.controllers;

import org.example.spring_security_cognito.models.Post;
import org.example.spring_security_cognito.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WallController {

    @Autowired
    PostService postService;

    @GetMapping("/wall")
    public String getWall(Model model, OAuth2AuthenticationToken token) {

        var posts = postService.getPosts();

        model.addAttribute("activePage", "wall");
        model.addAttribute("auth", token != null);
        if (token != null) {
            var username = token.getName();
            model.addAttribute("username", username);
        }

        model.addAttribute("newPost", new Post());
        model.addAttribute("posts", posts);

        return "wall";
    }

}
