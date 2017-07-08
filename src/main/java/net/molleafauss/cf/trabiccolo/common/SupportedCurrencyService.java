package net.molleafauss.cf.trabiccolo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Exposes the list of supported currencies from the config
 */
@Service
public class SupportedCurrencyService {

    private final List<String> supportedCurrencies;

    @Autowired
    public SupportedCurrencyService(@Value("${processor.supported.currencies}") String supportedCurrencies) {
        if(supportedCurrencies == null || supportedCurrencies.length() == 0) {
            throw new IllegalArgumentException("Supported currencies cannot be empty.");
        }
        this.supportedCurrencies = Arrays.asList(supportedCurrencies.split(",\\s?"));
    }

    public Iterator<String> enumCurrencies() {
        return supportedCurrencies.iterator();
    }
}
