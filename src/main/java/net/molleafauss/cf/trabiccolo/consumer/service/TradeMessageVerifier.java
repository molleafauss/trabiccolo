package net.molleafauss.cf.trabiccolo.consumer.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.*;

/**
 * Will verify if a TradeMessage received by the consumer can be processed, by running some basic
 * validity checks.
 */
@Component
public class TradeMessageVerifier {

    private final SupportedCountryService supportedCountryService;
    private final SupportedCurrencyService supportedCurrencyService;

    private DateTimeFormatter dateTimeFormatter;

    public TradeMessageVerifier(SupportedCountryService supportedCountryService,
                                SupportedCurrencyService supportedCurrencyService) {
        this.supportedCountryService = supportedCountryService;
        this.supportedCurrencyService = supportedCurrencyService;
        this.dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yy HH:mm:ss")
                .withZoneUTC()
                .withLocale(Locale.ENGLISH);
    }

    public void verify(TradeMessage tradeMessage)
            throws InvalidTradeMessageException {
        verifyCountry(tradeMessage.getOriginatingCountry());
        verifyCurrency(tradeMessage.getCurrencyFrom());
        verifyCurrency(tradeMessage.getCurrencyTo());
        verifyAmounts(tradeMessage);
        verifyDate(tradeMessage.getTimePlaced());
    }

    private void verifyCountry(String country) throws InvalidTradeMessageException {
        if(!supportedCountryService.supports(country)) {
            throw new InvalidTradeMessageException(UNSUPPORTED_COUNTRY);
        }
    }

    private void verifyCurrency(String currency) throws InvalidTradeMessageException {
        if(!supportedCurrencyService.supports(currency)) {
            throw new InvalidTradeMessageException(UNSUPPORTED_CURRENCY);
        }
    }

    private void verifyAmounts(TradeMessage tradeMessage) throws InvalidTradeMessageException {
        if(tradeMessage.getAmountBuy() * tradeMessage.getRate() != tradeMessage.getAmountSell()) {
            throw new InvalidTradeMessageException(INVALID_AMOUNT);
        }
    }

    private void verifyDate(String date) throws InvalidTradeMessageException {
        try {
            dateTimeFormatter.parseDateTime(date);
        } catch(IllegalArgumentException ex) {
            throw new InvalidTradeMessageException(INVALID_DATE);
        }
    }

}
