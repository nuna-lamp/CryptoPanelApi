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

    public DataFetcher getByCoinAndSumDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");
            String status = dataFetchingEnvironment.getArgument("status");
            String coin = dataFetchingEnvironment.getArgument("coin");
            String info = dataFetchingEnvironment.getArgument("info");

            return repository.getByCoinAndSum(from, to, status, coin, info);
        };
    }

    public DataFetcher getAllBetweenDatesAndStatusDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");
            String status = dataFetchingEnvironment.getArgument("status");
            String email = dataFetchingEnvironment.getArgument("email");

            return repository.getAllBetweenDatesAndStatus(from, to, status, email);
        };
    }

    public DataFetcher getAllBetweenDatesAndSumDataFetcher() {
        return dataFetchingEnvironment -> {
            String from = dataFetchingEnvironment.getArgument("fromDate");
            String to = dataFetchingEnvironment.getArgument("toDate");
            String status = dataFetchingEnvironment.getArgument("status");
            String amount = dataFetchingEnvironment.getArgument("amount");

            return repository.getAllBetweenDatesAndSum(from, to, status, amount);
        };
    }

    public DataFetcher getAllArgumentsDataFetcher() {
        return dataFetchingEnvironment -> {
            return repository.getAllArguments(dataFetchingEnvironment.getArguments());
        };
    }

    public DataFetcher getByLastNameAndFirsNameDataFetcher() {
        return dataFetchingEnvironment -> {
            String last_name = dataFetchingEnvironment.getArgument("last_name");
            String first_name = dataFetchingEnvironment.getArgument("first_name");

            return repository.findByLastNameAndFirsName(last_name,first_name);
        };
    }

}

