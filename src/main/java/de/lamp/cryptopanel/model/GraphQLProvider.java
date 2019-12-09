package de.lamp.cryptopanel.model;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;
    private GraphQL graphQL;

    @Autowired
    public GraphQLProvider(GraphQLDataFetchers graphQLDataFetchers) {
        this.graphQLDataFetchers = graphQLDataFetchers;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("Invoices.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("invoice", graphQLDataFetchers.getAllInvoicesDataFetcher())
                        //.dataFetcher("invoice", graphQLDataFetchers.getInvoicesByIdDataFetcher())
                        .dataFetcher("invoice_payment", graphQLDataFetchers.getAllInvoicesPaymentDataFetcher()))
                /*
                 .dataFetcher("invoicesById", graphQLDataFetchers.getInvoicesByIdDataFetcher())
                 .dataFetcher("uuid", graphQLDataFetchers.getInvoicesByUuidDataFetcher())
                 .dataFetcher("memo", graphQLDataFetchers.getInvoicesByMemoDataFetcher())
                 .dataFetcher("payment_id", graphQLDataFetchers.getInvoicesPaymentsDataFetcher())
                 .dataFetcher("email", graphQLDataFetchers.getInvoicesByEmailDataFetcher()))

                .type(newTypeWiring("Invoices")
                        .dataFetcher("id", graphQLDataFetchers.getInvoicesByIdDataFetcher()))
                */
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
