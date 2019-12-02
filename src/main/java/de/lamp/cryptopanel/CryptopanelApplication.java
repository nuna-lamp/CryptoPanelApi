package de.lamp.cryptopanel;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptopanelApplication {

    private static final Logger log = (Logger) LoggerFactory.getLogger(CryptopanelApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CryptopanelApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InvoicesRepository repository) {
        return (args) -> {

            // fetch all Invoices
            log.info("Invoices found with findAll():");
            log.info("-------------------------------");
            for (Invoices invoices : repository.findAll()) {
                log.info(invoices.toString());
            }
            log.info("");
        };



/*
            // fetch an individual customer by ID

            Invoices invoices = repository.findById(10L).get();
            log.info("Invoice found with findOne(1L):");
            log.info("--------------------------------");
            log.info(invoices.toString());
            log.info("");


            // fetch customers by last name

            log.info("Invoices found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Invoices bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");

 */
    }


}



