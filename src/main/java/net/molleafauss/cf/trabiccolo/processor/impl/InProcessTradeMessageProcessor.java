package net.molleafauss.cf.trabiccolo.processor.impl;

import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.processor.MessageProcessorService;
import net.molleafauss.cf.trabiccolo.processor.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Simple in-process processor of trade messages
 */
@Service
public class InProcessTradeMessageProcessor implements MessageProcessorService {

    private static Logger log = LoggerFactory.getLogger(InProcessTradeMessageProcessor.class);

    private final StatisticService statisticService;

    @Autowired
    public InProcessTradeMessageProcessor(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public void handle(TradeMessage message) {
        statisticService.processMessage(message);
        log.debug("Message received: {}", message);
    }

}
