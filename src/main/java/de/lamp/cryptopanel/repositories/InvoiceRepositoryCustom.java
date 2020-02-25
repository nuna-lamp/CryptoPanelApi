package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Amount;
import de.lamp.cryptopanel.model.CryptoCurrencies;
import de.lamp.cryptopanel.model.Endpoint;

import java.util.List;
import java.util.Map;

public interface InvoiceRepositoryCustom {

    Amount getByDates(String fromDate, String toDate, String status, String amount);

    CryptoCurrencies getByCoins(String fromDate, String toDate, String status, String coin, String info);

    List<Endpoint> getByEndpoints(Map<String, Object> arguments);

}

