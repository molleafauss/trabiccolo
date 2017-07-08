package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.processor.data.TransferKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Keeps track of the amount of currencyFrom/currencyTo that are sent per every country
 */
@Service
public class InboundCurrencyTrackerService extends CurrencyTrackerService implements ProcessorService {

    @Autowired
    public InboundCurrencyTrackerService(SupportedCountryService supportedCountryService,
                                         SupportedCurrencyService supportedCurrencyService) {
        super();
        initializeStatisticMap(supportedCountryService, supportedCurrencyService);
    }

    @Override
    public void processMessage(TradeMessage message) {
        TransferKey transferKey = new TransferKey(message.getOriginatingCountry(), message.getCurrencyFrom());
        incrementCountFor(transferKey);
    }
}
