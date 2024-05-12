package org.example.spring_security_cognito.controllers;

import org.example.spring_security_cognito.models.User;
import org.example.spring_security_cognito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getHomePage(OAuth2AuthenticationToken token, Model model) {

        model.addAttribute("activePage", "home");
        model.addAttribute("auth", token != null);

        if (token != null) {
            model.addAttribute("username", token.getName());
            var id = Objects.requireNonNull(token.getPrincipal().getAttribute("sub")).toString();
            userService.saveUser(new User(id, token.getName(), token.getPrincipal().getAttribute("email")));
        }

        return "index";
    }

}
