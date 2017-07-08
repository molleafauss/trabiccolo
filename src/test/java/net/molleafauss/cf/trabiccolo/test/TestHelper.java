package net.molleafauss.cf.trabiccolo.test;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;

public class TestHelper {

    public static TradeMessage.TradeMessageBuilder buildTestMessage() {
        return TradeMessage.builder()
                .userId("12345")
                .originatingCountry("IE")
                .amountBuy(1000.0)
                .amountSell(850.0)
                .rate(0.85)
                .currencyFrom("EUR")
                .currencyTo("GBP")
                .timePlaced("01-JUL-17 14:15:16");
    }

    public static SupportedCountryService testSupportedCountryService() {
        return new SupportedCountryService("IE,UK");
    }

    public static SupportedCurrencyService testSupportedCurrencyService() {
        return new SupportedCurrencyService("EUR,GBP");
    }
}
