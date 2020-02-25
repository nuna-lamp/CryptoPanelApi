package de.lamp.cryptopanel.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lamp.cryptopanel.CryptopanelApplication;
import de.lamp.cryptopanel.helper.AuthenticationHandler;
import de.lamp.cryptopanel.model.GraphQLProvider;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.GraphQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller    // This means that this class is a Controller
public class GraphQLController {
    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private GraphQL graphQL;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GraphQLProvider provider;

    private static final Logger log = (Logger) LoggerFactory.getLogger(CryptopanelApplication.class);

    public GraphQLController() {

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
                                @RequestHeader("userid") String userid,
                                @RequestHeader("Authorization") String token
    ) throws IOException {

        String query = (String) body.get("query");

        if (query == null) {
            query = "";
        }

        if (token == null) {
            token = "";
        }

        if (userid == null) {
            userid = "";
        }

        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        log.info(operationName);

        if (variables == null) {
            variables = new LinkedHashMap<>();
        } else {
            log.info(variables.toString());
        }

        if(!authenticationHandler.validateRequestAuthentication(operationName, token, Integer.parseInt(userid))) {
            throw new GraphQLException("Unauthorized");
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

}



