package de.lamp.cryptopanel.controllers;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class InvoicesControllerTest {

    @Autowired
    private  InvoicesRepository invoicesRepository;

    @Autowired
    private InvoicesController invoicesController;
   //InvoicesController invoicesController = new InvoicesRepositoryImpl();

    private Invoices invoices;
 /*
    private InvoicesController invoicesControllerTest;
    private InvoicesRepository invoicesRepository;
    private Invoices invoices;


    @Before
    public void setUP() throws Exception {
        // create mock object
        invoicesRepository = mock(InvoicesRepository.class);
        // create object controller to testing
        invoicesControllerTest = new InvoicesController(invoicesRepository);
        invoices = new Invoices();
    }
 */

    @Test
    public void getAllInvoices() throws Exception {
         //given
             //invoicesRepository.findAll();
        // when


       // then
            //Iterable<Invoices> result = invoicesControllerTest.getAllInvoices();
            //assert.assertThat(result, CoreMatchers.hasItem(invoices));
    }

    @Test
    public Object emailTesting(String email){

            if (email.isEmpty() || email.equalsIgnoreCase(invoices.getEmail())){
                throw new IllegalArgumentException("Email should be using example nuna@lamp-solutions.de");
            }
            return isNotNull();
    }

    @Test
    public void datesTesting(){

        Throwable exception = new IllegalArgumentException ();
        invoices.setCreated_at("");
        assertEquals("Date must be an SimpleDateFormat(yyyy-MM-dd)", exception.getMessage());
    }

     @Test
     public void NamesAssertions() {
          Invoices invoices = new Invoices();
          assertAll("invoice name",
              () -> assertEquals("Nuna", invoices.getFirst_name()),
              () -> assertEquals("Bopp", invoices.getLast_name())
          );
      }


    public void graphqlGET() {
    }

    @Test
    public void graphql() {
    }
}