package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.processor.data.TransferKey;
import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Keeps track of the amount of currencyFrom/currencyTo that are sent per every country
 */
@Service
public class StatisticService {

    private final Map<TransferKey, AtomicInteger> statisticMap;

    @Autowired
    public StatisticService(SupportedCountryService supportedCountryService,
                            SupportedCurrencyService supportedCurrencyService) {
        this.statisticMap = new HashMap<>();
        initializeStatisticMap(supportedCountryService, supportedCurrencyService);
    }

    private void initializeStatisticMap(SupportedCountryService supportedCountryService, SupportedCurrencyService supportedCurrencyService) {
        supportedCountryService.enumCountries().forEachRemaining(
                country -> supportedCurrencyService.enumCurrencies().forEachRemaining(
                        currencyFrom -> supportedCurrencyService.enumCurrencies().forEachRemaining(
                                currencyTo -> statisticMap.put(new TransferKey(country, currencyFrom, currencyTo), new AtomicInteger())
                        )
                )
        );
    }

    public void processMessage(TradeMessage message) {
        TransferKey transferKey = new TransferKey(message.getOriginatingCountry(), message.getCurrencyFrom(), message.getCurrencyTo());
        statisticMap.get(transferKey).incrementAndGet();
    }

    public List<TransferStatistic> listAllRelevantTransfers() {
        return statisticMap.entrySet().stream()
                .filter(item -> item.getValue().get() > 0)
                .map(this::freezeStatistic)
                .collect(Collectors.toList());
    }

    private TransferStatistic freezeStatistic(Map.Entry<TransferKey, AtomicInteger> item) {
        TransferKey key = item.getKey();
        int count = item.getValue().get();
        return new TransferStatistic(key.getCountry(), key.getCurrencyFrom(), key.getCurrencyTo(), count);
    }
}
