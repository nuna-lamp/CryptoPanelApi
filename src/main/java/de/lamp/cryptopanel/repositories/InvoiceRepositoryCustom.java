package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;

import java.util.List;

public interface InvoiceRepositoryCustom {

    List<Invoices> getAllBetweenDates(String fromDate, String toDate);
}
