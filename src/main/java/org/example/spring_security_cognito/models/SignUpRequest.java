package org.example.spring_security_cognito.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignUpRequest {

    private String username;
    private String password;
    private String email;

}
