package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.helper.RequestArgumentsHandler;
import de.lamp.cryptopanel.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        LocalDateTime now = LocalDateTime.now();
        ArgumentDateParseResult info = new ArgumentDateParseResult();

        Date fromDate = (new RequestArgumentsHandler()).parseDate(
                from,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info);

        Date toDate = (new RequestArgumentsHandler()).parseDate(
                to,
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                Invoices.defaultDateFormat,
                info);

        buildDateCriteria(criteriaBuilder, invoices, predicates, fromDate, toDate);

        query.select(criteriaBuilder.sum(invoices.get("amount")));

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

    private void buildDateCriteria(CriteriaBuilder criteriaBuilder,
                                   Root<Invoices> invoices,
                                   List<Predicate> predicates,
                                   Date startDate,
                                   Date endDate) {
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
        Root<Invoices> invoices = query.from(Invoices.class);
        Join<Invoices, Invoices_payments> join = invoices.join("invoices_payments");

        List<Predicate> predicates = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        ArgumentDateParseResult info = new ArgumentDateParseResult();

        Date fromDate = (new RequestArgumentsHandler()).parseDate(from,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info);

        Date toDate = (new RequestArgumentsHandler()).parseDate(
                to,
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                Invoices.defaultDateFormat,
                info);

        buildDateCriteria(criteriaBuilder, invoices, predicates, fromDate, toDate);

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        query.multiselect(
                criteriaBuilder.<Double>sum(invoices.get("amount")),
                join.get("currency")
        );
        query.groupBy(join.get("currency"));

        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

        return RequestArgumentsHandler.getCryptoCurrenciesTesting(tupleResult);
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
                root));

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        query.multiselect(
                criteriaBuilder.<Double>sum(root.get("amount")),
                root.get("endpoint")
        );
        query.groupBy(root.get("endpoint"));

        List<Tuple> tupleResult = entityManager.createQuery(query).getResultList();

        return RequestArgumentsHandler.getEndpointsTesting(tupleResult);
    }

}
