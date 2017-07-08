package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;

/**
 * Created by root on 08/07/2017.
 */
public interface ProcessorService {
    void processMessage(TradeMessage message);
}
