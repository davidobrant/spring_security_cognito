package org.example.spring_security_cognito.controllers;

import org.example.spring_security_cognito.configurations.Cognito;
import org.example.spring_security_cognito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @Value("${aws.cognito.poolId}")
    private String poolId;

    @GetMapping("/account")
    public String getAccountPage(Model model, OAuth2AuthenticationToken token) {

        model.addAttribute("activePage", "account");
        var sub = token.getPrincipal().getAttribute("sub");
        var username = token.getName();
        model.addAttribute("username", username);
        model.addAttribute("sub", sub);

        return "account";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(OAuth2AuthenticationToken token) {
        var id = Objects.requireNonNull(token.getPrincipal().getAttribute("sub")).toString();
        var username = token.getName();

        try {
            if (userService.deleteUser(id)) {
                Cognito.deleteUser(poolId, username);
//                new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            }
        } catch (Exception e) {
            return "redirect:/error";
        }

        return "delete-logout";
    }

}
