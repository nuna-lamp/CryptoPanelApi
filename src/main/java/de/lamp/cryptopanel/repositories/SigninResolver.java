package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.SigninPayload;
import de.lamp.cryptopanel.model.User;
import graphql.schema.GraphQLTypeResolvingVisitor;

public class SigninResolver {

    public User user(SigninPayload payload){
        return payload.getUser();
    }

}
