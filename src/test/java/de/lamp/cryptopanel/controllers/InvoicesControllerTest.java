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
class InvoicesControllerTest {

    private GraphQL graphQLMock;
    private ObjectMapper objectMapperMock;
    private GraphQLProvider graphQLProviderMock;

    private InvoicesController invoicesControllerMock;
    private UsersRepository usersRepositoryMock;
    private InvoicesRepository invoicesRepositoryMock;

    @BeforeEach
    public void setup() {
        graphQLMock = Mockito.mock(GraphQL.class);
        objectMapperMock = Mockito.mock(ObjectMapper.class);
        graphQLProviderMock = Mockito.mock(GraphQLProvider.class);
        invoicesControllerMock = Mockito.mock(InvoicesController.class);
        invoicesRepositoryMock = Mockito.mock(InvoicesRepository.class);
        usersRepositoryMock = Mockito.mock(UsersRepository.class);
    }

    @Test
    void graphqlGET() {
    }

    @Test
    void graphql() {
    }
}