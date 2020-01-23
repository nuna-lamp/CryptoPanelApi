package de.lamp.cryptopanel.controllers;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.InvoicesRepositoryImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.oneOf;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class InvoicesControllerTest {

    @Mock
    InvoicesRepository invoicesRepository;
    @InjectMocks
    InvoicesController invoicesController;
   //InvoicesController invoicesController = new InvoicesRepositoryImpl();

    @Mock
   InvoicesController.ChangeHandler changeHandler;

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
    public void graphqlGET() {
    }

    @Test
    public void graphql() {
    }
}