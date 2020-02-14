package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.User;
import graphql.GraphQLException;

public class AuthenticationHandler {

    public Boolean validateRequestAuthentication(String operationName, String token, User user) {

            if (isSecuredOperation(operationName)) {
                if (!isUserAuthenticated(user, token)) {
                    throw new GraphQLException("Unauthorized");
                }
                return true;
            }

        return true;
    }

    private Boolean isSecuredOperation(String operationName) {
        if (null == operationName) {
            return true;
        }
        if (operationName.equals("signIn")) {
            return false;
        }
        return true;
    }

    private Boolean isUserAuthenticated(User user, String token) {
        if (null == user) {
            return false;
        }
        if (token.equals(user.getToken())) {
            return true;
        }
        return false;
    }

}
