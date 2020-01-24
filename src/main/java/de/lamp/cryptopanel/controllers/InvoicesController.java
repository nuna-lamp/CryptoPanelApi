package de.lamp.cryptopanel.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/")
public class InvoicesController {
    @Autowired
    private InvoicesRepository repository;
    private GraphQL graphQL;
    private ObjectMapper objectMapper;

    public InvoicesController(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    public InvoicesController() {
    }

    public InvoicesController(InvoicesRepository invoicesRepository) {
    }

    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    // @GetMapping(path = "/all")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    Iterable<Invoices> getAllInvoices() {
        return repository.findAll();
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.GET)
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
    @RequestMapping(value = "/graphql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Map<String, Object> graphql(@RequestBody Map<String, Object> body) {
        String query = (String) body.get("query");

        if (query == null) {
            query = "";
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
