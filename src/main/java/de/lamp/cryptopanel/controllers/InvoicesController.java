package de.lamp.cryptopanel.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import de.lamp.cryptopanel.model.GraphQLDataFetchers;
import de.lamp.cryptopanel.model.GraphQLProvider;
import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller    // This means that this class is a Controller
public class InvoicesController {
    @Autowired
    private InvoicesRepository repository;
    private GraphQL graphQL;
    private ObjectMapper objectMapper;
    private GraphQLProvider provider;

    @Autowired
    public InvoicesController(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    public InvoicesController() {
    }



    public InvoicesController(InvoicesRepository invoicesRepository) {
    }


    @RequestMapping(value = "/graphql", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    Map<String, Object> graphqlGET(@RequestParam("query") String query,
                                   @RequestParam(value = "operationName", required = false) String operationName,
                                   @RequestParam("variables") String variablesJson) throws IOException {
        if (query == null) {
            query = "";
        }

        Map<String, Object> variables = new LinkedHashMap<>();

        if (variablesJson != null) {
            variables = objectMapper.readValue(variablesJson, new TypeReference<Map<String, Object>>() {
            });
        }

        return executeGraphqlQuery(operationName, query, variables);
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/graphql", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    Map<String, Object> graphql(@RequestBody Map<String, Object> body,
                                @RequestHeader("Authorization") String token
                                   ) throws IOException {

        String query = (String) body.get("query");

        if (query == null) {
            query = "";
        }

        if (token == null) {
            token = "";
        }
        if(!token.equals("test123")){
            throw new GraphQLException("Unauthorized");
        }


        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");

        if (variables == null) {
            variables = new LinkedHashMap<>();
        }


        return executeGraphqlQuery(operationName, query, variables);
    }

    private Map<String, Object> executeGraphqlQuery(String operationName, String query, Map<String, Object> variables) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                .build();


        return graphQL.execute(executionInput).toSpecification();
    }

    public interface ChangeHandler {
        void onChange();
    }


}
