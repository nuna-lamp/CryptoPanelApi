package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.model.Invoices_payments;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class InvoicesRepositoryImplTest {

    @Autowired
    InvoicesRepository invoicesRepository;

    @Autowired
    EntityManager entityManager;

    @Before
    public void setUp() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Invoices> root = query.from(Invoices.class);
        Join<Invoices, Invoices_payments> join = root.join("invoices_payments");


        entityManager = null;
        // endPoint = donateForm , amount = 3
        entityManager.persist( new Invoices(1, "" ,"","","","",
                "","","","","","","",
                3.00,"",2,"","","donateForm",1,"",
                "","","",""));

        // endPoint = paymentForm, amount = 2
        entityManager.persist( new Invoices(2, "" ,"","","","",
                "","","","","","","",
                2.00,"",2,"","","paymentForm",5,"",
                "","","",""));

        // endPoint = donateForm, amount = 4
        entityManager.persist( new Invoices(3, "" ,"","","","",
                "","","","","","","",
                4.00,"",2,"","","donateForm",5,"",
                "","","",""));


        entityManager.persist(new Invoices_payments(1, 1,"","" ,2.00,
                "","","","","",""));

        entityManager.persist(new Invoices_payments(2, 3,"","" ,5.00,
                "","","","","",""));

        entityManager.persist(new Invoices_payments(3, 3,"","" ,3.00,
                "","","","","",""));
    }


    @Test
    public void getByDates() {
    }

    @Test
    public void getByCoins() {
    }

    @Test
    void getByEndpoints() {
        Invoices invoices = new Invoices();
        assertAll("Endpoint",
                () -> assertEquals("donateForm", invoices.getEndpoint()),
                () -> assertEquals("paymentForm", invoices.getEndpoint())
        );
    }


  /*
    @Test
    List<Endpoint> getByEndpoints(Map<String, Object> arguments {

        //given
        Invoices invoices = new Invoices();
        invoices.setEmail("nuna@lamp-solutions.de");
        invoices.setFirst_name("Nuna");
        invoices.setLast_name("Bopp");

        // List<Predicate> predicates = new ArrayList<>();

        //whenn


        //then
        //assertEquals();
    }

   */

}

