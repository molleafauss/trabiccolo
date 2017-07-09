package net.molleafauss.cf.trabiccolo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Exposes the list of supported currencies from the config
 */
@Service
public class SupportedCurrencyService {

    private final Set<String> supportedCurrencySet;

    public SupportedCurrencyService(@Value("${processor.supported.currencies}") String supportedCurrencies) {
        if(supportedCurrencies == null || supportedCurrencies.length() == 0) {
            throw new IllegalArgumentException("Supported currencies cannot be empty.");
        }
        this.supportedCurrencySet = new HashSet<>();
        this.supportedCurrencySet.addAll(Arrays.asList(supportedCurrencies.split(",\\s?")));
    }

    public boolean supports(String currency) {
        return supportedCurrencySet.contains(currency);
    }
}
