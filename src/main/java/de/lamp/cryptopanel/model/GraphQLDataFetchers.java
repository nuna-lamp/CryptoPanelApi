package de.lamp.cryptopanel.model;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import de.lamp.cryptopanel.CryptopanelApplication;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.UsersRepository;
import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
            HashMap auth = dataFetchingEnvironment.getArgument("auth");

            String email = auth.get("email").toString();

            User user = usersRepository.findOneByEmail(email);
            String token = user.getToken();

            SigninPayload returnPayload = new SigninPayload(token, user);
            return returnPayload;
        };
    }

}

