package net.molleafauss.cf.trabiccolo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Exposes the list of supported countries from the config
 */
@Service
public class SupportedCountryService {

    private final List<String> supportedCountries;

    @Autowired
    public SupportedCountryService(@Value("${processor.supported.countries}") String supportedCountries) {
        if(supportedCountries == null || supportedCountries.length() == 0) {
            throw new IllegalArgumentException("Supported Countries cannot be empty.");
        }
        this.supportedCountries = Arrays.asList(supportedCountries.split(",\\s?"));
    }

    public Iterator<String> enumCountries() {
        return supportedCountries.iterator();
    }
}
