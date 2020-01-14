package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Amount;
import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.model.Invoices_payments;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class InvoicesRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    InvoicesRepository invoicesRepository;

    @PersistenceContext
    EntityManager entityManager;
/*
   public Amount getCoinAndSum(String from,
                             String to,
                             String status,
                             String coin,
                             String amount) {
        //   String[] currency = {"DASH", "LTC", "BTC", "BCH"};
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        CriteriaQuery<String > queryCoin = criteriaBuilder.createQuery(String.class);

        Root<Invoices> invoices = query.from(Invoices.class);
        Root<Invoices_payments> invoices_payments = query.from(Invoices_payments.class);

        Invoices join = (Invoices) invoices_payments.get("currency");

        List<Predicate> predicates = new ArrayList<>();


        if (!(null == coin || coin.equals(""))) {
            predicates.add(criteriaBuilder.equal(invoices_payments.get("currency"), coin));
        }

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

        /*
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoices> inv_ = criteriaBuilder.createQuery(Invoices.class);
        CriteriaQuery<Invoices_payments> invp_ = criteriaBuilder.createQuery(Invoices_payments.class);
        Root<Invoices> invoices = inv_.from(Invoices.class);
        Join<Invoices_payments, Currency> currencyJoin = query.join(Invoices_.payment_id).join(Invoices_payments_.curenncy);

        // ListJoin<Invoices, Invoices_payments> tasks = invoices.join();

        /*
        Metamodel m = entityManager.getMetamodel();
        EntityType<Invoices> Invoices_ = m.entity(Invoices.class);
        EntityType<Invoices_payments> Owner_ = m.entity(Invoices_payments.class);

        Root<Invoices> invoices1 = query.from(Invoices.class);
        Join<Invoices_payments, Currency> currencyJoin = query.join(Invoices_.currency).join(Invoices_payments_.addresses);

        return namount;


    }
*/
    @Override
    public Amount getAllBetweenDatesAndSum(
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

    @Override
    public List<Invoices> getAllBetweenDatesAndArguments(Map<String, Object> arguments) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoices> query = criteriaBuilder.createQuery(Invoices.class);
        Root<Invoices> invoices = query.from(Invoices.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {

            if (null != entry && entry.getKey().equals("status")) {
                predicates.add(criteriaBuilder.equal(invoices.get(entry.getKey()), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("email")) {
                predicates.add(criteriaBuilder.equal(invoices.get(entry.getKey()), entry.getValue()));
            } else if (!(null == entry) || entry.getValue().equals("last_name")) {
                predicates.add(criteriaBuilder.equal(invoices.get(entry.getKey()), entry.getValue()));
            }
        }

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public List getAllBetweenDatesAndStatus(
            String from,
            String to,
            String status,
            String email) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoices> query = criteriaBuilder.createQuery(Invoices.class);
        Root<Invoices> invoices = query.from(Invoices.class);

        List<Predicate> predicates = new ArrayList<>();


        if (!(null == status || status.equals(""))) {
            predicates.add(criteriaBuilder.equal(invoices.get("status"), status));
        }
        if (!(null == email || email.equals(""))) {
            predicates.add(criteriaBuilder.equal(invoices.get("email"), email));
        }

        for (int i = 0; i < predicates.size(); i++) {
            query.where(predicates.toArray(new Predicate[i]));
        }

        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public List<Invoices> findByLastNameAndFirsName(String last_name, String first_name) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `invoices` WHERE last_name =:lname or first_name =:fname ", Invoices.class);

        query.setParameter("lname", last_name);
        query.setParameter("fname", first_name);
        return query.getResultList();
    }

}
