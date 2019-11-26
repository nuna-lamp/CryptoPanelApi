package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.entities.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
public interface InvoicesRepository extends CrudRepository<Invoices, Integer> {

   List<Invoices> findById(int id);
   List<Invoices> findByLastNameStartsWithIgnoreCase(String last_name);
}