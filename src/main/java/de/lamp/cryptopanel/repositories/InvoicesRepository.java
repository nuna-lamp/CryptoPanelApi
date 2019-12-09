package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.model.Status;
import graphql.schema.DataFetcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

//public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
public interface InvoicesRepository extends CrudRepository<Invoices, Long> {

    List<Invoices> findById(int id);

  //  Invoices findByTypeAndId(Class<?> type, Long id);

  //  List<Invoices> findById(Class<Invoices> invoicesClass, Long valueOf);


    // List findByInvoiceStatus(Invoices invoices);

    //  Invoices findByInvoiceId(Class<Invoices> type, Long id);
    //  Object findById(String id);
    //  Object findById(Class<Invoices> invoicesClass, Long valueOf);
    // List<Invoices> findByLastNameStartsWithIgnoreCase(String last_name);

}