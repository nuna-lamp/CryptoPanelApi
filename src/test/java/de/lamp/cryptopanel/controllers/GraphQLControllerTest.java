package de.lamp.cryptopanel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.lamp.cryptopanel.model.GraphQLProvider;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.UsersRepository;
import graphql.GraphQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
class GraphQLControllerTest {

    private GraphQL graphQLMock;
    private ObjectMapper objectMapperMock;
    private GraphQLProvider graphQLProviderMock;
    private GraphQLController graphQLControllerMock;

    @BeforeEach
    public void setup() {
        graphQLMock = Mockito.mock(GraphQL.class);
        objectMapperMock = Mockito.mock(ObjectMapper.class);
        graphQLProviderMock = Mockito.mock(GraphQLProvider.class);
        graphQLControllerMock = Mockito.mock(GraphQLController.class);

    }

    @Test
    void graphqlGET() {
    }

    @Test
    void graphql() {
    }
}