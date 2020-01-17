package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Amount;
import de.lamp.cryptopanel.model.CryptoCurrencies;
import de.lamp.cryptopanel.model.Invoices;

import java.util.List;
import java.util.Map;

public interface InvoiceRepositoryCustom {

    CryptoCurrencies getByCoinAndSum(String fromDate, String toDate, String status, String coin, String info);

    List<Invoices> getAllBetweenDatesAndStatus(String fromDate, String toDate, String status, String email);

    Amount getAllBetweenDatesAndSum(String fromDate, String toDate, String status, String amount);

    List<Invoices> getAllArguments(Map<String, Object> arguments);

    List<Invoices> findByLastNameAndFirsName(String last_name, String first_name);

}
