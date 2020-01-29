package de.lamp.cryptopanel.helper;


import de.lamp.cryptopanel.model.*;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class RequestArgumentsHandler {

    public List<Predicate> buildPredicateListForFromArguments(Map<String, Object> arguments,
                                                              CriteriaBuilder criteriaBuilder,
                                                              Root<Invoices> root,
                                                              Join<Invoices, Invoices_payments> joinMock){

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

    public static void getStatusTesting(String status, CriteriaBuilder criteriaBuilder, Root<Invoices> invoices, List<Predicate> predicates) {
        if (!(null == status || status.equals(""))) {
            predicates.add(criteriaBuilder.equal(invoices.get("status"), status));
        }
    }

    public ArgumentDateParseResult getStringInfosDateformate(String from,
                                                             String to,
                                                             CriteriaBuilder criteriaBuilder,
                                                             Root<Invoices> invoices,
                                                             List<Predicate> predicates) {

        ArgumentDateParseResult result = new ArgumentDateParseResult();
        result.success = true;

        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            startDate = dateFormat.parse(from);
            result.info = "Showing invoices from: " + startDate.toString();
        } catch (Exception e) {
            cal.set(Calendar.YEAR, 1970);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
            result.info = "Could not parse startDate, using 1970-01-01";
            result.success = false;
        }

        try {
            endDate = dateFormat.parse(to);
        } catch (Exception e) {
            LocalDateTime now = LocalDateTime.now();

            cal.set(Calendar.YEAR, now.getYear());
            cal.set(Calendar.MONTH, now.getMonthValue());
            cal.set(Calendar.DAY_OF_MONTH, now.getDayOfMonth());
            endDate = cal.getTime();
            result.info = result.info + " Could not parse endDate, using " + endDate.toString();
            result.success = false;
        }

        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(invoices.get(
                "created_at").as(java.sql.Date.class), startDate);
        predicates.add(startDatePredicate);

        predicates.add(
                criteriaBuilder.lessThanOrEqualTo(invoices.get(
                        "created_at").as(java.sql.Date.class), endDate)
        );

        return result;
    }

    public static CryptoCurrencies getCoinCurrencies(String info, List<Tuple> tupleResult) {

        CryptoCurrencies result = new CryptoCurrencies();

        for (Tuple t : tupleResult) {
            switch (t.get(1).toString()) {
                case "DASH":
                    result.dash = (double) t.get(0);

                    break;
                case "BTC":
                    result.btc = (double) t.get(0);

                    break;
                case "LTC":
                    result.ltc = (double) t.get(0);

                    break;
                case "BCH":
                    result.bch = (double) t.get(0);

                    break;
                default:
            }
        }
        result.info = info;

        return result;
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

    public static void buildPradicateSize(CriteriaQuery<Tuple> query, List<Predicate> predicates) {

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }
    }

}
