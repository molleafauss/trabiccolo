package net.molleafauss.cf.trabiccolo.processor.service;

import lombok.extern.slf4j.Slf4j;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

/**
 * Increments metrics for the sold currency of each message
 */
@Service
@Slf4j
public class CurrencyToMetricService implements ProcessorService {

    private static final String SOLD_CURRENCY_METRIC = "trabiccolo.processor.message.currencyTo.%s.%s";

    private final CounterService counterService;

    public CurrencyToMetricService(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public void processMessage(TradeMessage message) {
        if(message.getCurrencyTo() == null || message.getOriginatingCountry() == null) {
            log.warn("Won't calculate metrics for invalid message: {}", message);
            return;
        }
        counterService.increment(String.format(SOLD_CURRENCY_METRIC, message.getCurrencyTo(), message.getOriginatingCountry()));
    }
}
