package de.lamp.cryptopanel.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lamp.cryptopanel.CryptopanelApplication;
import de.lamp.cryptopanel.helper.AuthenticationHandler;
import de.lamp.cryptopanel.model.GraphQLProvider;
import de.lamp.cryptopanel.model.User;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.UsersRepository;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.GraphQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller    // This means that this class is a Controller
public class InvoicesController {
    @Autowired
    private InvoicesRepository repository;
    @Autowired
    private UsersRepository usersRepository;
    private GraphQL graphQL;
    private ObjectMapper objectMapper;
    private GraphQLProvider provider;

    private static final Logger log = (Logger) LoggerFactory.getLogger(CryptopanelApplication.class);

    @Autowired
    public InvoicesController(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    public InvoicesController() {
    }

    public InvoicesController(InvoicesRepository invoicesRepository, UsersRepository usersRepository) {
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

        User user = usersRepository.findOneById(Integer.parseInt(userid));
        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        log.info(operationName);

        if (variables == null) {
            variables = new LinkedHashMap<>();
        } else {
            log.info(variables.toString());
        }

        AuthenticationHandler authenticationHandler = new AuthenticationHandler();

        if(!authenticationHandler.validateRequestAuthentication(operationName, token, user)) {
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

/*
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/user/getEmployeesList")
                .hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
                .permitAll().and().logout().permitAll();

        http.csrf().disable();
    }


    public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin")
                .authorities("ROLE_ADMIN");
    }

    @Configuration
    @EnableAuthorizationServer
    public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

             public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory().withClient("javainuse").secret("secret").authorizedGrantTypes("authorization_code")
                    .scopes("read").authorities("CLIENT");
        }
    }

 */



