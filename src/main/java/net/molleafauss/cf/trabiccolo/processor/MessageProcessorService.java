package net.molleafauss.cf.trabiccolo.processor;

import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;

/**
 * Interface which will perform the consuming of messages.
 */
public interface MessageProcessorService {

    void handle(TradeMessage tradeMessage);

}
