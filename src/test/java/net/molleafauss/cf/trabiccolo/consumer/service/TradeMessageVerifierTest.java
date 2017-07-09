package net.molleafauss.cf.trabiccolo.consumer.service;

import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.*;
import static net.molleafauss.cf.trabiccolo.test.TestHelper.*;

public class TradeMessageVerifierTest {

    private TradeMessageVerifier verifier;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        verifier = new TradeMessageVerifier(testSupportedCountryService(), testSupportedCurrencyService());
    }

    @Test
    public void messageWithCorrectFieldsShouldPass() throws Exception {
        verifier.verify(buildTestMessage().build());
    }

    @Test
    public void messageWithUnsupportedCountryWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_COUNTRY);

        verifier.verify(buildTestMessage().originatingCountry("IT").build());
    }

    @Test
    public void messageWithNullCountryWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_COUNTRY);

        verifier.verify(buildTestMessage().originatingCountry(null).build());
    }

    @Test
    public void messageWithUnsupportedCurrencyFromWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_CURRENCY);

        verifier.verify(buildTestMessage().currencyFrom("USD").build());
    }

    @Test
    public void messageWithNullCurrencyFromWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_CURRENCY);

        verifier.verify(buildTestMessage().currencyFrom(null).build());
    }

    @Test
    public void messageWithUnsupportedCurrencyToWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_CURRENCY);

        verifier.verify(buildTestMessage().currencyTo("USD").build());
    }

    @Test
    public void messageWithNullCurrencyToWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(UNSUPPORTED_CURRENCY);

        verifier.verify(buildTestMessage().currencyTo(null).build());
    }

    @Test
    public void messageWithWrongAmountsWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(INVALID_AMOUNT);

        verifier.verify(buildTestMessage().amountSell(1.0).build());
    }

    @Test
    public void messageWithUnparseableDateWillThrowException() throws Exception {
        exception.expect(InvalidTradeMessageException.class);
        exception.expectMessage(INVALID_DATE);

        verifier.verify(buildTestMessage().timePlaced("blalala").build());
    }
}