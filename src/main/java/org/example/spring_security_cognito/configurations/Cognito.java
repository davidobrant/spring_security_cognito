package org.example.spring_security_cognito.configurations;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.List;

public class Cognito {

    private static CognitoIdentityProviderClient getClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.EU_NORTH_1)
                .build();
    }

    public static String signUp(String clientId, String username, String password, String email) {
        AttributeType userAttrs = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        List<AttributeType> userAttrsList = new ArrayList<>();
        userAttrsList.add(userAttrs);
        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(userAttrsList)
                    .username(username)
                    .clientId(clientId)
                    .password(password)
                    .build();

            return getClient().signUp(signUpRequest).userSub();
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return null;
    }

    public static void confirmUser(String poolId, String username) {
        try {
            AdminConfirmSignUpRequest request = AdminConfirmSignUpRequest.builder()
                    .userPoolId(poolId)
                    .username(username)
                    .build();

            getClient().adminConfirmSignUp(request);
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public static boolean deleteUser(String poolId, String username) {
        try {
            AdminDeleteUserRequest request = AdminDeleteUserRequest.builder()
                    .userPoolId(poolId)
                    .username(username)
                    .build();

            getClient().adminDeleteUser(request);
            return true;
        } catch (CognitoIdentityProviderException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
