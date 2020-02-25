package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.*;
import graphql.GraphQLException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
class AuthenticationHandlerTest {

    @BeforeEach
    public void setup() {

    }

    @Test
    void validateRequestAuthenticationTestSignIn() {
        User user = new User();
        user.setName("nuna");
        user.setId(1);
        user.setPassword("test");

        String operationName = "signIn";
        String token = "test12345";

        boolean result = new AuthenticationHandler().validateRequestAuthentication(operationName, token, user);

        Assert.assertEquals(result, true);
    }

    @Test
    void validateRequestAuthenticationTestSecretOperation() {
        User user = new User();
        user.setName("nuna");
        user.setId(1);
        user.setPassword("test");

        String operationName = "sendthemoney";
        String token = "test12345";

        try {
            boolean result = new AuthenticationHandler().validateRequestAuthentication(operationName, token, user);
            Assert.assertEquals(result, false);
        } catch (GraphQLException anGraphQLException) {
            Assert.assertEquals(anGraphQLException.getMessage(), new GraphQLException("Unauthorized").getMessage());
        }

    }

    @Test
    void validateRequestAuthenticationValidToken() {
        User user = new User();
        user.setName("nuna");
        user.setId(2);
        user.setPassword("test");

        String operationName = "sendthemoney";
        String token = "8fb652cb0da8ded07c9dc505368d1c6af7d68cb84fa1d92de22c855ccf3d9643";

        boolean result = new AuthenticationHandler().validateRequestAuthentication(operationName, token, user);

        Assert.assertEquals(result, true);

    }

}