package net.molleafauss.cf.trabiccolo.processor.impl;

import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.processor.MessageProcessorService;
import net.molleafauss.cf.trabiccolo.processor.service.ProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Simple in-process dispatcher of message received to all registered services.
 */
@Service
public class InProcessTradeMessageProcessor implements MessageProcessorService {

    private static Logger log = LoggerFactory.getLogger(InProcessTradeMessageProcessor.class);

    private final List<ProcessorService> processorServices;

    @Autowired
    public InProcessTradeMessageProcessor(List<ProcessorService> processorServices) {
        this.processorServices = processorServices;
    }

    @Override
    public void handle(TradeMessage message) {
        processorServices.forEach(processor -> processor.processMessage(message));
        log.debug("Message received: {}", message);
    }

}
