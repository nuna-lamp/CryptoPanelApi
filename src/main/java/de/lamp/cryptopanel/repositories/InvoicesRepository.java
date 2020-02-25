package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoicesRepository extends JpaRepository<Invoices, Long>, InvoiceRepositoryCustom{

    List<Invoices> findById(int id);

    List<Invoices> findAll();


}