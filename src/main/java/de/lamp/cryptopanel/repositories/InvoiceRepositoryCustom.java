package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;

import java.util.List;

public interface InvoiceRepositoryCustom {

    List<Invoices> getAllBetweenDatesAndStatus(String fromDate, String toDate, String status);

    List<Invoices> findByLastNameAndFirsName(String last_name, String first_name);

    List<Invoices> findByEmail(String email);

    List<Invoices> findByPayment(String payment);
}
