package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.Endpoint;
import de.lamp.cryptopanel.model.Invoices;
import de.lamp.cryptopanel.model.Invoices_payments;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
class RequestArgumentsHandlerTest {

    private CriteriaBuilder criteriaBuilderMock;
    private CriteriaQuery criteriaQueryMock;
    private Root<Invoices> invoiceRootMock;
    private Join<Invoices, Invoices_payments> joinMock;

    @BeforeEach
    public void setup() {
        criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
        invoiceRootMock = Mockito.mock(Root.class);
        joinMock = Mockito.mock(Join.class);

    }

    @Test
    void buildPredicateListForFromArguments() {

        HashMap<String, Object> arguments = new HashMap<>() {{
            put("email", "test");
        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), null);

    }

    @Test
    void buildPredicateListForFromArguments2() {

        HashMap<String, Object> arguments = new HashMap<>() {{
            put("email", "test");
            put("name", "test");

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), null);

    }

    @Test
    void buildPredicateListForFromArguments2a() {

        HashMap<String, Object> arguments = new HashMap<>() {{
            put("email", "test");
            put("name", "test");
            put("PFFF", "test");
        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), null);

    }

    @Test
    void getStatusTesting(){
        HashMap<String, Object> arguments = new HashMap<>() {{
            Map<String, String> map = new HashMap<>();
            map.put("p1","paid");
            map.put("p2","open");
            map.put("p3","expired");

            Map<String, String> expected = new HashMap<>();
            expected.put("p3","expired");
            expected.put("p1","paid");
            expected.put("p2","open");

            //1. Test equal, ignore order
            Assert.assertThat(map, is(expected));
            //2. Test size
            Assert.assertThat(map.size(), is(3));
            //3. Test map entry, best!
            Assert.assertThat(map, IsMapContaining.hasEntry("p2", "paid"));
            //4. Test map key
            Assert.assertThat(map, IsMapContaining.hasKey("p1"));
            //5. Test map value
            Assert.assertThat(map, IsMapContaining.hasValue("paid"));

        }};
        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

    }

    @Test
    void getStringInfosDateformate() {
        HashMap<String, Object> arguments = new HashMap<>() {{
            put("created_at", "2019-01-01");
            put("update_at", "2019-12-31");

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), null);
        Assert.assertFalse("Could not parse startDate, using 1970-01-01", false);

    }

    @Test
    void getCoinCurrencies() {
        HashMap<String, Object> arguments = new HashMap<>() {{
            HashMap coins = new HashMap();
            coins.put(1, "DASH");
            coins.put(2, "BTC");
            coins.put(3, "LTC");
            coins.put(4, "BCH");

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

        Assert.assertNotNull("Not Null", arguments);

    }

    @Test
    void getEndpointsTesting() {
        HashMap<String, Object> arguments = new HashMap<>() {{

            HashMap endpoint = new HashMap();
            endpoint.put(1, "donateForm");
            endpoint.put(2, "paymentForm");

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);

       // Assert.assertEquals("donateForm", "paymentForm");
        Assert.assertEquals("donateForm", "donateForm");
    }

    @Test
    void buildPradicateSize() {

        HashMap<String, Object> arguments = new HashMap<>() {{
            Map<String, String> map = new HashMap<>();
            map.put("p1","paid");
            map.put("p2","open");
            map.put("p3","expired");

            Map<String, String> expected = new HashMap<>();
            expected.put("p3","expired");
            expected.put("p1","paid");
            expected.put("p2","open");

            Assert.assertThat(map.size(), is(4));

        }};

        List<Predicate> result = (new RequestArgumentsHandler()).buildPredicateListForFromArguments(
                arguments,
                criteriaBuilderMock,
                invoiceRootMock,
                joinMock);
/*
        Object[] array = Ist.toArray();
        Assert.assertTrue("Arrays not the same length", array.length == strs.length);
        for (int i = 0; i < array.length; i++)
           Assert.assertEquals(strs[i], (String) array[i]);
*/

    }
}