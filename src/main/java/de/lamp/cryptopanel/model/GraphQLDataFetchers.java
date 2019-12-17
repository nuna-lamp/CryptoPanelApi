package de.lamp.cryptopanel.model;

import de.lamp.cryptopanel.repositories.InvoicesRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private InvoicesRepository repository;

    @Autowired
    public GraphQLDataFetchers(InvoicesRepository repository) {
        this.repository = repository;
    }

    private InvoicesRepository getRepository() {
        return repository;
    }

    public DataFetcher getInvoiceIdsDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer invoiceId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return repository.findById(invoiceId).get(0);
        };
    }

    public DataFetcher getInvoicesPaymentIdDataFetcher() {
        return dataFetchingEnvironment -> {
            List list = new ArrayList<Map<String, Object>>();
            return repository.findById(1).get(0);
        };
    }

    public DataFetcher getInvoicesDataFetcher() {
        return dataFetchingEnvironment -> {
            return repository.findAll();
        };
    }

    public DataFetcher getAllBetweenDatesDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");

            return repository.getAllBetweenDates(from, to);
        };
    }
}

