package net.molleafauss.cf.trabiccolo.processor.service;

import lombok.extern.slf4j.Slf4j;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

/**
 * Increments metrics for the originating country of each message
 */
@Service
@Slf4j
public class OriginCountryMetricService implements ProcessorService {

    private static final String ORIGINCOUNTRY_METRIC = "trabiccolo.processor.message.originatingCountry.%s";

    private final CounterService counterService;

    public OriginCountryMetricService(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public void processMessage(TradeMessage message) {
        if(message.getOriginatingCountry() == null) {
            log.warn("Won't calculate metrics for invalid message: {}", message);
            return;
        }
        counterService.increment(String.format(ORIGINCOUNTRY_METRIC, message.getOriginatingCountry()));
    }
}
