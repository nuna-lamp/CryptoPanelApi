package de.lamp.cryptopanel.model;

import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.UsersRepository;
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
    private UsersRepository usersRepository;

    @Autowired
    public GraphQLDataFetchers(InvoicesRepository repository, UsersRepository usersRepository)
    {
        this.repository = repository;
        this.usersRepository = usersRepository;
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

    public DataFetcher signinUserDataFetcher() {
        return dataFetchingEnvironment -> {
            //dataFetchingEnvironment.g
            //String email =  dataFetchingEnvironment.getArgument("email");
            //return usersRepository.findOneByEmail(email);

            User user = usersRepository.findOneByEmail("nuna@bopp.de");

            SigninPayload returnPayload = new SigninPayload(1234, user);
            return returnPayload;
        };
    }

}

