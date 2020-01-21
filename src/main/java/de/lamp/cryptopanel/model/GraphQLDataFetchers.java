package de.lamp.cryptopanel.model;

import de.lamp.cryptopanel.repositories.InvoicesRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public DataFetcher getInvoicesDataFetcher() {
        return dataFetchingEnvironment -> {
            return repository.findAll();
        };
    }

    public DataFetcher getByDatesDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");
            String status = dataFetchingEnvironment.getArgument("status");
            String amount = dataFetchingEnvironment.getArgument("amount");

            return repository.getByDates(from, to, status, amount);
        };
    }

    public DataFetcher getByCoinsDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");
            String status = dataFetchingEnvironment.getArgument("status");
            String coin = dataFetchingEnvironment.getArgument("coin");
            String info = dataFetchingEnvironment.getArgument("info");

            return repository.getByCoins(from, to, status, coin, info);
        };
    }

    public DataFetcher getByEndpointsDataFetcher() {
        return dataFetchingEnvironment -> {
            return repository.getByEndpoints(dataFetchingEnvironment.getArguments());
        };
    }
}

