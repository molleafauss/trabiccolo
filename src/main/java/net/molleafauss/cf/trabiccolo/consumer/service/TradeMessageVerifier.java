package net.molleafauss.cf.trabiccolo.consumer.service;

import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.UNSUPPORTED_COUNTRY;
import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.UNSUPPORTED_CURRENCY;

/**
 * Will verify if a TradeMessage received by the consumer can be processed, by checking supported countries
 * and currencies.
 */
@Component
public class TradeMessageVerifier {

    private final Set<String> supportedCurrencies;
    private final Set<String> supportedCountries;

    @Autowired
    public TradeMessageVerifier(SupportedCountryService supportedCountryService,
                                SupportedCurrencyService supportedCurrencyService) {
        this.supportedCurrencies = new HashSet<>();
        supportedCurrencyService.enumCurrencies().forEachRemaining(supportedCurrencies::add);
        this.supportedCountries = new HashSet<>();
        supportedCountryService.enumCountries().forEachRemaining(supportedCountries::add);
    }

    public void verify(TradeMessage tradeMessage)
            throws InvalidTradeMessageException {
        verifyCountry(tradeMessage.getOriginatingCountry());
        verifyCurrency(tradeMessage.getCurrencyFrom());
        verifyCurrency(tradeMessage.getCurrencyTo());
    }

    private void verifyCountry(String country) throws InvalidTradeMessageException {
        if(!supportedCountries.contains(country)) {
            throw new InvalidTradeMessageException(UNSUPPORTED_COUNTRY);
        }
    }

    private void verifyCurrency(String currency) throws InvalidTradeMessageException {
        if(!supportedCurrencies.contains(currency)) {
            throw new InvalidTradeMessageException(UNSUPPORTED_CURRENCY);
        }
    }

}
