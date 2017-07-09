package net.molleafauss.cf.trabiccolo.consumer.service;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

/**
 * Will publish metrics related to the consumption of messages
 */
@Service
public class ConsumerMetricsService {

    private static final String MESSAGE_CONSUMED_METRIC = "trabiccolo.consumer.message.received";
    private static final String MESSAGE_INVALID_METRIC = "trabiccolo.consumer.message.invalid";

    private final CounterService counterService;

    public ConsumerMetricsService(CounterService counterService) {
        this.counterService = counterService;
    }


    public void reportConsumedMessage() {
        counterService.increment(MESSAGE_CONSUMED_METRIC);
    }

    public void reportInvalidMessage() {
        counterService.increment(MESSAGE_INVALID_METRIC);
    }
}
