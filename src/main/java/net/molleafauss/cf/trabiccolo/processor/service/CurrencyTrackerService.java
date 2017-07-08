package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.common.SupportedCountryService;
import net.molleafauss.cf.trabiccolo.common.SupportedCurrencyService;
import net.molleafauss.cf.trabiccolo.processor.data.TransferKey;
import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class CurrencyTrackerService {

    private final Map<TransferKey, AtomicInteger> currencyMap;

    CurrencyTrackerService() {
        this.currencyMap = new HashMap<>();
    }

    void initializeStatisticMap(SupportedCountryService supportedCountryService, SupportedCurrencyService supportedCurrencyService) {
        supportedCountryService.enumCountries().forEachRemaining(
            country -> supportedCurrencyService.enumCurrencies().forEachRemaining(
                        currency -> currencyMap.put(new TransferKey(country, currency), new AtomicInteger())
                )
        );
    }

    void incrementCountFor(TransferKey currencyFromKey) {
        currencyMap.get(currencyFromKey).incrementAndGet();
    }

    public List<TransferStatistic> listAllRelevantTransfers() {
        return currencyMap.entrySet().stream()
                .filter(item -> item.getValue().get() > 0)
                .map(this::freezeStatistic)
                .collect(Collectors.toList());
    }

    private TransferStatistic freezeStatistic(Map.Entry<TransferKey, AtomicInteger> item) {
        TransferKey key = item.getKey();
        int count = item.getValue().get();
        return new TransferStatistic(key.getCountry(), key.getCurrency(), count);
    }
}
