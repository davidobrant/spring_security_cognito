package org.example.spring_security_cognito.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.spring_security_cognito.models.Post;
import org.example.spring_security_cognito.models.User;
import org.example.spring_security_cognito.services.PostService;
import org.example.spring_security_cognito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Controller
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @GetMapping("/posts")
    public String getPostsPage(Model model, OAuth2AuthenticationToken token) {
        model.addAttribute("activePage", "posts");
        model.addAttribute("auth", token != null);

        assert token != null;
        model.addAttribute("username", token.getName());

        var currentUserId = Objects.requireNonNull(token.getPrincipal().getAttribute("sub")).toString();

        var posts = postService.getPostsByUserId(currentUserId);

        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new Post());

        return "posts";
    }

    @PostMapping("/posts/new")
    public String postPost(@ModelAttribute Post newPost, OAuth2AuthenticationToken token, HttpServletRequest request) {
        var currentUser = userService.getAuthUser(token);
        newPost.setUser(currentUser);

        postService.createPost(newPost);

        String referer = request.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        } else {
            return "redirect:/wall";
        }
    }

    @PostMapping("/posts/{postId}/edit")
    public String postEditPost(@RequestParam("id") Long id,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @PathVariable Long postId,
                               HttpServletRequest request,
                               OAuth2AuthenticationToken token
    ) {

        if (title.isEmpty() || content.isEmpty()) {
            return "redirect:/wall";
        }

        var post = postService.getPostById(postId);

        if (post == null) {
            return "redirect:/wall";
        }

        post.setTitle(title);
        post.setContent(content);

        if (!isAuthPost(token, post)) return "redirect:/wall";

        postService.updatePost(post);

        String referer = request.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        } else {
            return "redirect:/wall";
        }
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, HttpServletRequest request) {

        postService.deletePostById(id);

        String referer = request.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        } else {
            return "redirect:/wall";
        }
    }

    private boolean isAuthPost(OAuth2AuthenticationToken token, Post post) {
        return token.getName().equalsIgnoreCase(post.getUser().getUsername());
    }
}
