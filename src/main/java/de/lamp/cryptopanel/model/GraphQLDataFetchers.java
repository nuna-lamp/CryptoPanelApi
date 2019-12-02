package de.lamp.cryptopanel.model;

import com.google.common.collect.ImmutableMap;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private InvoicesRepository repository;

    @Autowired
    public GraphQLDataFetchers(InvoicesRepository repository) {
        this.repository = repository;
    }

    private InvoicesRepository getRepository() {
        return repository;
    }

    private static List<Map<String, Object>> invoices = Arrays.asList(
            ImmutableMap.of("id", "invoice-1",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878411",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -1",
                    "email", ""),
            ImmutableMap.of("id", "invoice-2",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878412",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -2",
                    "email", ""),
            ImmutableMap.of("id", "invoice-3",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878415",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -4",
                    "email", ""
                    /*
                    "return_url", "",
                    "callback_url", "",
                    "expires_at", "",
                    "created_at", "",
                    "update_at", "",
                    "seller_name", "",
                    "amount", "",
                    "currency", "",*/
                    //"payment_id", "Invoices_payments -1",
                    /*"cancel_url", "",
                    "extra_data", "",
                    "endpoint", "",
                    "doi", "",
                    "ip", "",
                    "option_timestamp", "",
                    "selected_currencies", "",
                    "endpoint_version", "",*/
                    //"note", ""
            )
    );

    private static List<Map<String, Object>> invoices_payments = Arrays.asList(
            ImmutableMap.of("id", "invoices_payment-1",
                    "invoid_id", " ",
                    "uuid", " ",
                    "curency", " "),
            ImmutableMap.of("id", "invoices_payment-2",
                    "invoid_id", " ",
                    "uuid", " ",
                    "curency", " "),
            ImmutableMap.of("id", "invoices_payment-3",
                    "invoid_id", " ",
                    "uuid", " ",
                    "curency", " ")

                    /*"amount", " ",
                    "electrum_amount", " ",
                    "electrum_id", " ",
                    "electrum_uri", " ",
                    "electrum_address", " ",
                    "electrum_expires_at"," ",
                    "created_at", " ",
                    "update_at", " "*/
    );

    public DataFetcher getAllInvoicesDataFetcher() {
        return dataFetchingEnvironment -> {
            List list = new ArrayList<Map<String, Object>>();

            List inv = (List) repository.findAll();
            Iterator<Invoices> it = inv.iterator();
            while (it.hasNext()) {
                Invoices current = it.next();
                list.add(current);
            }
            Invoices invoice=repository.findById(2).get(0);

            return invoice;
           /* return ( List<Map<String, Object>>) repository.findAll();*/
          /*  return invoices
                    .stream()
                    .filter(invoices -> invoices.get("uuid").equals("e90c7a90-5796-11e9-8f48-3da631878411"))
                    .findFirst()
                    .orElse(null);*/
        };

    }

    public DataFetcher getInvoicesByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String invoicesId = dataFetchingEnvironment.getArgument("id");
            return invoices
                    .stream()
                    .filter(invoices -> invoices.get("id").equals(invoicesId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getInvoicesByUuidDataFetcher() {
        return dataFetchingEnvironment -> {
            String uuid = dataFetchingEnvironment.getArgument("uuid");
            return invoices
                    .stream()
                    .filter(invoices -> invoices.get("uuid").equals(uuid))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getInvoicesByMemoDataFetcher() {
        return dataFetchingEnvironment -> {
            String memo = dataFetchingEnvironment.getArgument("memo");
            return invoices
                    .stream()
                    .filter(invoices -> invoices.get("memo").equals(memo))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getInvoicesByEmailDataFetcher() {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email");
            return invoices
                    .stream()
                    .filter(invoices -> invoices.get("email").equals(email))
                    .findFirst()
                    .orElse(null);
        };
    }

    public List<Map<String, Object>> findInvoiceById(int id) {
        List str = new ArrayList(invoices);

        List inv = repository.findById(id);
        Iterator<Invoices> it = inv.iterator();
        while (it.hasNext()) {
            Invoices invoices = it.next();
            inv.addAll(repository.findById(id));
        }
        return invoices;
    }

    public DataFetcher getInvoicesPaymentsDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> invoices = dataFetchingEnvironment.getSource();
            String paymentId = invoices.get("invoices_paymentId");
            return invoices_payments
                    .stream()
                    .filter(invoices_payment -> invoices_payment.get("id").equals(paymentId))
                    .findFirst()
                    .orElse(null);
        };
    }
}
