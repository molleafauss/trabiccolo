package net.molleafauss.cf.trabiccolo.consumer.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.UNSUPPORTED_COUNTRY;
import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.UNSUPPORTED_CURRENCY;

/**
 * Will verify if a TradeMessage received by the consumer can be processed, by checking supported countries
 * and currencies.
 */
@Component
public class TradeMessageVerifier {

    private final SupportedCountryService supportedCountryService;
    private final SupportedCurrencyService supportedCurrencyService;

    @Autowired
    public TradeMessageVerifier(SupportedCountryService supportedCountryService,
                                SupportedCurrencyService supportedCurrencyService) {
        this.supportedCountryService = supportedCountryService;
        this.supportedCurrencyService = supportedCurrencyService;
    }

    public void verify(TradeMessage tradeMessage)
            throws InvalidTradeMessageException {
        verifyCountry(tradeMessage.getOriginatingCountry());
        verifyCurrency(tradeMessage.getCurrencyFrom());
        verifyCurrency(tradeMessage.getCurrencyTo());
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

}
