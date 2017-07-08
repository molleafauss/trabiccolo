package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.processor.data.TransferKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tracks the currency that are sent from a country.
 */
@Service
public class OutboundCurrencyTrackerService extends CurrencyTrackerService implements ProcessorService {

    @Autowired
    public OutboundCurrencyTrackerService(SupportedCountryService supportedCountryService,
                                          SupportedCurrencyService supportedCurrencyService) {
        super();
        initializeStatisticMap(supportedCountryService, supportedCurrencyService);
    }

    @Override
    public void processMessage(TradeMessage message) {
        TransferKey transferKey = new TransferKey(message.getOriginatingCountry(), message.getCurrencyTo());
        incrementCountFor(transferKey);
    }
}
