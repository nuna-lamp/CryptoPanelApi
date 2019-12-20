package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;

import java.util.List;
import java.util.Map;

public interface InvoiceRepositoryCustom {

    List<Invoices> getAllBetweenDatesAndStatus(String fromDate, String toDate, String status, String email);

    List<Invoices> getAllBetweenDatesAndArguments(Map<String, Object> arguments);

    List<Invoices> findByLastNameAndFirsName(String last_name, String first_name);

    List<Invoices> findByEmail(String email);

    List<Invoices> findByPayment(String payment);
}
