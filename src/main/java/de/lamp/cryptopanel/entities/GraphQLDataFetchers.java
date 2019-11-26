package de.lamp.cryptopanel.entities;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> invoices = Arrays.asList(
            ImmutableMap.of("id", "invoices-1",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878411",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -1"),
            ImmutableMap.of("id", "invoices-1",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878412",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -2"),
            ImmutableMap.of("id", "invoices-1",
                    "uuid", "e90c7a90-5796-11e9-8f48-3da631878415",
                    "memo", "Unterstütze Bezahlen mit Krypto mit einer Spende",
                    "payment_id", "Invoices_payments -4"

                    // "email", "nuna@lamp-solutions.de"
                    //"first_name", "Nuna",
                    //"last_name", "Bopp",
                    /*"status", "",
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

    private static List<Map<String, String>> invoices_payments = Arrays.asList(
            ImmutableMap.of("id", "invoices_payment-1",
                    "invoid_id", " ",
                    "uuid", " ",
                    "curency", " "),
            ImmutableMap.of("id", "invoices_payment-1",
                    "invoid_id", " ",
                    "uuid", " ",
                    "curency", " "),
            ImmutableMap.of("id", "invoices_payment-1",
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

    public DataFetcher getInvoicesPaymentsDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> invoices = dataFetchingEnvironment.getSource();
            String invoices_paymentId = invoices.get("invoices_paymentId");
            return invoices_payments
                    .stream()
                    .filter(invoices_payment -> invoices_payment.get("id").equals(invoices_paymentId))
                    .findFirst()
                    .orElse(null);
        };
    }
}


