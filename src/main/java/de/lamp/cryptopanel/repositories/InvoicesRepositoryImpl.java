package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class InvoicesRepositoryImpl implements InvoiceRepositoryCustom {

    @Autowired
    InvoicesRepository invoicesRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List getAllBetweenDatesAndStatus(String from, String to, String status) {

        if (null == status || status.equals("")) {
            Query query = entityManager.createNativeQuery(
                    "SELECT " +
                            "   * " +
                            "FROM " +
                            "   `invoices`" +
                            " WHERE " +
                            "   created_at BETWEEN :from AND :to",
                    Invoices.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            return query.getResultList();
        } else {
            Query query = entityManager.createNativeQuery(
                    "SELECT " +
                            "   * " +
                            "FROM " +
                            "   `invoices`" +
                            " WHERE " +
                            "   created_at BETWEEN :from AND :to" +
                            " AND " +
                            "   status = :status",
                    Invoices.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            query.setParameter("status", status);
            return query.getResultList();
        }
    }

    @Override
    public List<Invoices> findByLastNameAndFirsName(String last_name, String first_name) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `invoices` WHERE last_name =:lname or first_name =:fname ", Invoices.class);

        query.setParameter("lname", last_name);
        query.setParameter("fname", first_name);
        return query.getResultList();
    }

    @Override
    public List<Invoices> findByEmail(String email) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `invoices` WHERE email like :email", Invoices.class);

        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public List<Invoices> findByPayment(String payment) {
        Query query = entityManager.createNativeQuery(
                "SELECT sum(amount) FROM `invoices` WHERE payment_id IS NOT NULL ", Invoices.class);

        query.setParameter(1, payment);
        return query.getResultList();
    }
}
