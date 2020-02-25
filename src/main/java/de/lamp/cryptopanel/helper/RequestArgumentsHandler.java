package de.lamp.cryptopanel.helper;


import de.lamp.cryptopanel.model.ArgumentDateParseResult;
import de.lamp.cryptopanel.model.CryptoCurrencies;
import de.lamp.cryptopanel.model.Endpoint;
import de.lamp.cryptopanel.model.Invoices;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestArgumentsHandler {

    public List<Predicate> buildPredicateListForFromArguments(Map<String, Object> arguments,
                                                              CriteriaBuilder criteriaBuilder,
                                                              Root<Invoices> root) {

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
            if (!(null == entry) || entry.equals("email")) {
                predicates.add(criteriaBuilder.equal(root.get("email"), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("last_name")) {
                predicates.add(criteriaBuilder.equal(root.get("last_name"), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("first_name")) {
                predicates.add(criteriaBuilder.equal(root.get("first_name"), entry.getValue()));
            }
        }

        return predicates;
    }

    /**
     * @param dateString
     * @param defaultYear
     * @param defaultMonth
     * @param defaultDay
     * @param pattern
     * @return
     */
    public Date parseDate(
            String dateString,
            int defaultYear,
            int defaultMonth,
            int defaultDay,
            String pattern,
            ArgumentDateParseResult info) {

        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar cal = Calendar.getInstance();

        try {
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            cal.set(Calendar.YEAR, defaultYear);
            cal.set(Calendar.MONTH, defaultMonth);
            cal.set(Calendar.DAY_OF_MONTH, defaultDay);
            date = cal.getTime();
            info.info += "Could not parse " + dateString + ", using " + date.toString();
            info.success = false;
        }
        return date;
    }

    public static List<Endpoint> getEndpointsTesting(List<Tuple> tupleResult) {

        List<Endpoint> results = new ArrayList<>();

        for (Tuple t : tupleResult) {
            Endpoint res = new Endpoint();
            res.endpoint = t.get(1).toString();
            res.amount = Double.parseDouble(t.get(0).toString());
            results.add(res);
        }

        return results;
    }

    public static CryptoCurrencies getCryptoCurrenciesTesting(List<Tuple> tupleResult) {
        CryptoCurrencies coins =new CryptoCurrencies();

        for (Tuple t : tupleResult) {

            switch (t.get(1).toString()) {
                case "DASH":
                    coins.dash =  Double.parseDouble(t.get(0).toString());
                    break;
                case "BTC":
                    coins.btc = Double.parseDouble(t.get(0).toString());
                    break;
                case "LTC":
                    coins.ltc =  Double.parseDouble(t.get(0).toString());
                    break;
                case "BCH":
                    coins.bch =  Double.parseDouble(t.get(0).toString());
                    break;
                default:
            }
        }

        return coins;
    }

}
