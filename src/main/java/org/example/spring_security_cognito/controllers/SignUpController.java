package org.example.spring_security_cognito.controllers;

import org.example.spring_security_cognito.models.SignUpRequest;
import org.example.spring_security_cognito.models.User;
import org.example.spring_security_cognito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.example.spring_security_cognito.configurations.Cognito;

@Controller
public class SignUpController {

    @Autowired
    UserService userService;

    @Value("${aws.cognito.poolId}")
    private String poolId;
    @Value("${aws.cognito.registration.clientId}")
    private String clientId;

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("signUp", new SignUpRequest());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String postSignUp(@ModelAttribute SignUpRequest request) {
        var username = request.getUsername();
        var email = request.getEmail();
        var sub = Cognito.signUp(clientId, username, request.getPassword(), email);
        if (sub != null) {
            Cognito.confirmUser(poolId, username);
            userService.saveUser(new User(sub, username, email));
            return "redirect:/oauth2/authorization/cognito";
        }
        return "redirect:/error";
    }

}
