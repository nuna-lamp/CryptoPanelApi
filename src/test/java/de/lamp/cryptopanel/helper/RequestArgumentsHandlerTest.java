package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.model.Invoices_payments;
import de.lamp.cryptopanel.repositories.InvoicesRepository;
import org.hamcrest.CoreMatchers;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
class RequestArgumentsHandlerTest {

    private CriteriaBuilder criteriaBuilderMock;
    private CriteriaQuery criteriaQueryMock;
    private Root<Invoices> invoiceRootMock;


    @BeforeEach
    public void setup(){
        criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
        invoiceRootMock = Mockito.mock(Root.class);

    }

    @Test
    void buildPredicateListForFromArguments() {

        HashMap<String,Object> arguments = new HashMap<>() {{
           put("email" ,"test");
        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock);

        Assert.assertEquals(result.size(),1);
        Assert.assertEquals(result.get(0),null);

    }

    @Test
    void buildPredicateListForFromArguments2() {

        HashMap<String,Object> arguments = new HashMap<>() {{
            put("email" ,"test");
            put("name" ,"test");

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock);

        Assert.assertEquals(result.size(),2);
        Assert.assertEquals(result.get(0),null);

    }

    @Test
    void buildPredicateListForFromArguments2a() {

        HashMap<String,Object> arguments = new HashMap<>() {{
            put("email" ,"test");
            put("name" ,"test");
            put("PFFF" ,"test");
        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock);

        Assert.assertEquals(result.size(),2);
        Assert.assertEquals(result.get(0),null);

    }

}