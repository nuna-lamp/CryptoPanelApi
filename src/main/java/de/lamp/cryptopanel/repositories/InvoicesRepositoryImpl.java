package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.CryptopanelApplication;
import de.lamp.cryptopanel.helper.RequestArgumentsHandler;
import de.lamp.cryptopanel.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class InvoicesRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    InvoicesRepository invoicesRepository;

    @PersistenceContext
    EntityManager entityManager;

     @Override
    public Amount getByDates(
            String from,
            String to,
            String status,
            String amount) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Invoices> invoices = query.from(Invoices.class);

        List<Predicate> predicates = new ArrayList<>();

        java.util.Date startDate = null;
        java.util.Date endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String info = new String();

        try {
            startDate = dateFormat.parse(from);
            info = "Showing invoices from: " + startDate.toString();
        } catch (Exception e) {
            cal.set(Calendar.YEAR, 1970);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
            info = "Could not parse startDate, using 1970-01-01";
        }

        try {
            endDate = dateFormat.parse(to);
        } catch (Exception e) {
            LocalDateTime now = LocalDateTime.now();

            cal.set(Calendar.YEAR, now.getYear());
            cal.set(Calendar.MONTH, now.getMonthValue());
            cal.set(Calendar.DAY_OF_MONTH, now.getDayOfMonth());
            endDate = cal.getTime();
            info = info + " Could not parse endDate, using " + endDate.toString();
        }

        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(invoices.get(
                "created_at").as(java.sql.Date.class), startDate);
        predicates.add(startDatePredicate);

        predicates.add(
                criteriaBuilder.lessThanOrEqualTo(invoices.get(
                        "created_at").as(java.sql.Date.class), endDate)
        );

        query.select(criteriaBuilder.sum(invoices.get("amount")));

        if (!(null == status || status.equals(""))) {
            predicates.add(criteriaBuilder.equal(invoices.get("status"), status));
        }

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        TypedQuery<Double> typedQuery = entityManager.createQuery(query);
        Double sum = typedQuery.getSingleResult();

        Amount namount = new Amount();
        namount.amount = sum;
        namount.info = info;
        return namount;
    }

    public CryptoCurrencies getByCoins(String from,
                                            String to,
                                            String status,
                                            String coin,
                                            String infos) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Invoices> root = query.from(Invoices.class);
        Join<Invoices, Invoices_payments> join = root.join("invoices_payments");

        List<Predicate> predicates = new ArrayList<>();

        java.util.Date startDate = null;
        java.util.Date endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String info = new String();

        try {
            startDate = dateFormat.parse(from);
        } catch (Exception e) {
            cal.set(Calendar.YEAR, 1970);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
            info = "Could not parse startDate, using 1970-01-01";
        }
        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get(
                "created_at").as(java.sql.Date.class), startDate);
        predicates.add(startDatePredicate);

        try {
            endDate = dateFormat.parse(to);
        } catch (Exception e) {
            LocalDateTime now = LocalDateTime.now();
            cal.set(Calendar.YEAR, now.getYear());
            cal.set(Calendar.MONTH, now.getMonthValue());
            cal.set(Calendar.DAY_OF_MONTH, now.getDayOfMonth());
            endDate = cal.getTime();
            info = info + " Could not parse endDate, using " + endDate.toString();
        }

        Predicate toDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get(
                "created_at").as(java.sql.Date.class), endDate);
        predicates.add(toDatePredicate);

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        query.multiselect(
                criteriaBuilder.<Double>sum(root.get("amount")),
                join.get("currency")
        );
        query.groupBy(join.get("currency"));

        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

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

    @Override
    public List<Endpoint> getByEndpoints(Map<String, Object> arguments) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Invoices> root = query.from(Invoices.class);
        Join<Invoices, Invoices_payments> join = root.join("invoices_payments");

        List<Predicate> predicates = (new RequestArgumentsHandler().buildPredicateListForFromArguments(
                arguments,
                criteriaBuilder,
                root
        ));


        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        query.multiselect(
                criteriaBuilder.<Double>sum(root.get("amount")),
                root.get("endpoint")
        );
        query.groupBy(root.get("endpoint"));

        List<Endpoint> results = new ArrayList<>();
        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

        for (Tuple t : tupleResult) {
            Endpoint res = new Endpoint();
            res.endpoint = t.get(1).toString();
            res.amount = Double.parseDouble(t.get(0).toString());
            results.add(res);
        }

        return results;
    }

}
