package net.molleafauss.cf.trabiccolo.processor.service;

import lombok.extern.slf4j.Slf4j;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

/**
 * Extracts some statistics from the message and sends them to the counter service.
 */
@Service
@Slf4j
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
        if(message.getOriginatingCountry() == null || message.getCurrencyFrom() == null || message.getCurrencyTo() == null) {
            log.warn("Won't calculate metrics for invalid message: {}", message);
            return;
        }
        counterService.increment(String.format(METRIC_FOR_COUNTRY, message.getOriginatingCountry()));
        counterService.increment(String.format(METRIC_FOR_FROM_CURRENCY, message.getCurrencyFrom(), message.getOriginatingCountry()));
        counterService.increment(String.format(METRIC_FOR_TO_CURRENCY, message.getCurrencyTo(), message.getOriginatingCountry()));
    }
}
