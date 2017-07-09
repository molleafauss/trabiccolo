package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

/**
 * Extracts some statistics from the message and sends them to the counter service.
 */
@Service
public class CurrencyTrackerMetricService implements ProcessorService {

    private static final String METRIC_FOR_COUNTRY = "trabiccolo.processor.message.originatingCountry.%s";
    private static final String METRIC_FOR_FROM_CURRENCY = "trabiccolo.processor.message.currencyFrom.%s.%s";
    private static final String METRIC_FOR_TO_CURRENCY = "trabiccolo.processor.message.currencyTo.%s.%s";

    private final CounterService counterService;

    @Autowired
    public CurrencyTrackerMetricService(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public void processMessage(TradeMessage message) {
        if(message.getOriginatingCountry() == null) {
            return;
        }
        counterService.increment(String.format(METRIC_FOR_COUNTRY, message.getOriginatingCountry()));
        if(message.getCurrencyFrom() != null) {
            counterService.increment(String.format(METRIC_FOR_FROM_CURRENCY, message.getCurrencyFrom(), message.getOriginatingCountry()));
        }
        if(message.getCurrencyTo() != null) {
            counterService.increment(String.format(METRIC_FOR_FROM_CURRENCY, message.getCurrencyTo(), message.getOriginatingCountry()));
        }
    }
}
