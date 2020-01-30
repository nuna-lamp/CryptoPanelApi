package de.lamp.cryptopanel.repositories;

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

    private static String defaultDateFormat =  "yyyy-MM-dd";

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
        LocalDateTime now = LocalDateTime.now();
        ArgumentDateParseResult info = new ArgumentDateParseResult();

        Date fromDate = (new RequestArgumentsHandler()).parseDate(
                from,1970,
                Calendar.JANUARY,
                1,
                defaultDateFormat,
                info);

        Date toDate = (new RequestArgumentsHandler()).parseDate(
                to,
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                defaultDateFormat,
                info);

        buildDateCriteria(criteriaBuilder, invoices, predicates,fromDate,toDate);

        query.select(criteriaBuilder.sum(invoices.get("amount")));

        RequestArgumentsHandler.getStatusTesting(status, criteriaBuilder, invoices, predicates);

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        TypedQuery<Double> typedQuery = entityManager.createQuery(query);
        Double sum = typedQuery.getSingleResult();

        Amount namount = new Amount();
        namount.amount = sum;
        namount.info = info.info;
        return namount;
    }

    private void buildDateCriteria( CriteriaBuilder criteriaBuilder,
                                    Root<Invoices> invoices,
                                    List<Predicate> predicates,
                                    Date startDate,
                                    Date endDate){
        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(invoices.get(
                "created_at").as(java.sql.Date.class), startDate);
        predicates.add(startDatePredicate);

        predicates.add(
                criteriaBuilder.lessThanOrEqualTo(invoices.get(
                        "created_at").as(java.sql.Date.class), endDate)
        );
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

        // String info = (new RequestArgumentsHandler()).getStringInfosDateformate(from, to, criteriaBuilder, invoices, predicates);

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

        RequestArgumentsHandler.buildPradicateSize(query, predicates);

        query.multiselect(
                criteriaBuilder.<Double>sum(root.get("amount")),
                join.get("currency")
        );
        query.groupBy(join.get("currency"));

        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

        return RequestArgumentsHandler.getCoinCurrencies(info, tupleResult);
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
                root,
                join));

        RequestArgumentsHandler.buildPradicateSize(query, predicates);

        query.multiselect(
                criteriaBuilder.<Double>sum(root.get("amount")),
                root.get("endpoint")
        );
        query.groupBy(root.get("endpoint"));

        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

        return RequestArgumentsHandler.getEndpointsTesting(tupleResult);
    }

}
