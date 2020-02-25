package de.lamp.cryptopanel.helper;

import de.lamp.cryptopanel.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.hamcrest.Matchers.matchesPattern;


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
    void parseDate() {
        String dateString = new String("2018-05-01");
        ArgumentDateParseResult info = new ArgumentDateParseResult();
        RequestArgumentsHandler handler = new RequestArgumentsHandler();

        Date testDate = handler.parseDate(
                dateString,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info
                );
        Assert.assertEquals(info.success, true);

        LocalDate localDate =
                testDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Assert.assertEquals(2018,localDate.getYear());
        Assert.assertEquals(5, localDate.getMonthValue());

        dateString="asdasdsad";
        testDate = handler.parseDate(
                dateString,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info
        );

        localDate =
                testDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Assert.assertEquals(info.success, false);
        Assert.assertNotEquals(info.info,"Could not parse Date");
        Assert.assertEquals(1970, localDate.getYear());
        Assert.assertEquals(1,localDate.getMonthValue());

        dateString="1960-01-01";
        testDate = handler.parseDate(
                dateString,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info
        );

        localDate =
                testDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Assert.assertEquals(1960, localDate.getYear());
        Assert.assertEquals(1, localDate.getMonthValue());
        Assert.assertFalse("Date less then",false);

        dateString="2020-13-31";
        testDate = handler.parseDate(
                dateString,
                1970,
                Calendar.JANUARY,
                1,
                Invoices.defaultDateFormat,
                info
        );

        localDate =
                testDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(info.success, false);

        Assert.assertEquals(2020, localDate.getYear());
        Assert.assertEquals(12, localDate.getMonthValue());
       // Assert.assertFalse("Date greater then ",false);
        Assert.assertThat(Invoices.defaultDateFormat, matchesPattern("\\d{4}-\\d{2}-\\d{2}"));

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
    void getCryptoCurrenciesTesting() {

        Tuple tuple = new MockTupleCoins("dash",3.14d);
        Tuple tuple2 = new MockTupleCoins("ltc",7.14d);
        Tuple tuple3 = new MockTupleCoins("bch",7.14d);


        List<Tuple> arguments = new ArrayList<>();
        arguments.add(tuple);
        arguments.add(tuple2);
        arguments.add(tuple3);


        CryptoCurrencies result = (new RequestArgumentsHandler()).getCryptoCurrenciesTesting((List<Tuple>) arguments);

        Assert.assertEquals(
                result.dash,
                Double.parseDouble(tuple.get(0).toString()),
                0.00001
        );


        Assert.assertEquals(
                result.dash,
                3.14d,
                0.00001
        );
        Assert.assertEquals(
                result.ltc,
                7.14d,
                0.00001
        );
        Assert.assertEquals(
                result.bch,
                7.14d,
                0.00001
        );

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
            return new Object[] {
                    one.getAlias().toLowerCase(),
                    two.getAlias().toLowerCase(),
                    };
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

    public static class MockTupleCoins implements Tuple {

        TupleElement<String> amount = new StringTupleElement("3.14");
        TupleElement<String> coin;

        MockTupleCoins(String coin, Double amount){
            this.coin = new StringTupleElement(coin);
            this.amount = new StringTupleElement(amount.toString());
        }

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
            if(i==0) {
                return (X) amount.getAlias();
            }
            if(i==1) {
                return (X) coin.getAlias().toUpperCase();
            }
            return (X) String.valueOf(i);
        }

        @Override
        public Object get(int i) {
            return get(i, Object.class);
        }

        @Override
        public Object[] toArray() {
            return new Object[]{

            };
        }

        @Override
        public List<TupleElement<?>> getElements() {
            return Arrays.asList();
        }

        public class StringTupleElement implements TupleElement<String> {
            private final String value;

            public StringTupleElement(String value) {
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