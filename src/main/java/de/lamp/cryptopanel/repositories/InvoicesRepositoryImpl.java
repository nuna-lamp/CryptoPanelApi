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

    public List<Invoices> getAllBetweenDates(String from, String to) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `invoices` WHERE created_at BETWEEN :from AND :to", Invoices.class);

        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();
    }

}
