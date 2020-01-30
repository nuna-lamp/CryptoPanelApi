package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.ArgumentDateParseResult;
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

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.*;

import java.util.*;

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
    void getStatusTesting() {
        HashMap<String, Object> arguments = new HashMap<>() {{
            Map<String, String> map = new HashMap<>();
            map.put("p1", "paid");
            map.put("p2", "open");
            map.put("p3", "expired");

            Map<String, String> expected = new HashMap<>();
            expected.put("p3", "expired");
            expected.put("p1", "paid");
            expected.put("p2", "open");

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

        List<Predicate> predicates = new ArrayList<>();
        String from = new String("2018-01-01");

        ArgumentDateParseResult result = (new RequestArgumentsHandler()).parseDate(
                from,
                to,
                criteriaBuilderMock,
                invoiceRootMock,
                predicates);

        Assert.assertEquals(result.success, true);
        Assert.assertEquals(result.success, true);


        from = new String("test1234");
        to = new String("2019-12-31");

        result = (new RequestArgumentsHandler()).getStringInfosDateformate(
                from,
                to,
                criteriaBuilderMock,
                invoiceRootMock,
                predicates);

        Assert.assertEquals(result.success, false);
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

        Tuple tuple = new MockTuple();
        List<Tuple> arguments = new ArrayList<>();
        arguments.add(tuple);

        List<Endpoint> result = (new RequestArgumentsHandler()).getEndpointsTesting((List<Tuple>) arguments);

        Assert.assertEquals(
                result.get(0).amount,
                Double.parseDouble(tuple.get(0).toString()),
                0.1
        );

    }

    @Test
    void buildPradicateSize() {

        HashMap<String, Object> arguments = new HashMap<>() {{
            Map<String, String> map = new HashMap<>();
            map.put("p1", "paid");
            map.put("p2", "open");
            map.put("p3", "expired");

            Map<String, String> expected = new HashMap<>();
            expected.put("p3", "expired");
            expected.put("p1", "paid");
            expected.put("p2", "open");

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

    @SuppressWarnings("unchecked")
    private static class MockTuple implements Tuple {

        TupleElement<String> one = new StringTupleElement("oNe");
        TupleElement<String> two = new StringTupleElement("tWo");

        @Override
        public <X> X get(TupleElement<X> tupleElement) {
            return (X) get(tupleElement.getAlias());
        }

        @Override
        public <X> X get(String alias, Class<X> type) {
            return (X) get(alias);
        }

        @Override
        public Object get(String alias) {
            return alias.toLowerCase();
        }

        @Override
        public <X> X get(int i, Class<X> type) {
            return (X) String.valueOf(i);
        }

        @Override
        public Object get(int i) {
            return get(i, Object.class);
        }

        @Override
        public Object[] toArray() {
            return new Object[] { one.getAlias().toLowerCase(), two.getAlias().toLowerCase() };
        }

        @Override
        public List<TupleElement<?>> getElements() {
            return Arrays.asList(one, two);
        }

        private static class StringTupleElement implements TupleElement<String> {

            private final String value;

            private StringTupleElement(String value) {
                this.value = value;
            }

            @Override
            public Class<? extends String> getJavaType() {
                return String.class;
            }

            @Override
            public String getAlias() {
                return value;
            }
        }
    }


}