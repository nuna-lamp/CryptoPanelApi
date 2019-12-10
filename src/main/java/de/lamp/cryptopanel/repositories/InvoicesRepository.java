package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoicesRepository extends CrudRepository<Invoices, Long> {

    List<Invoices> findById(int id);

}