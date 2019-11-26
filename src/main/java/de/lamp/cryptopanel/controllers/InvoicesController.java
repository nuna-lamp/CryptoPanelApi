package de.lamp.cryptopanel.controllers;

import de.lamp.cryptopanel.entities.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/")
public class InvoicesController {
    @Autowired
    private InvoicesRepository Repository;

    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Invoices> getAllInvoices() {
        // This returns a JSON or XML with the users
        return Repository.findAll();
    }

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewInvoices(@RequestParam String name,
                          @RequestParam String email) {

        Invoices n = new Invoices();
        n.setLastName(name);
        n.setEmail(email);
        Repository.save(n);
        return "Saved";

    }
}
