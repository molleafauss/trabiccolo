package net.molleafauss.cf.trabiccolo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Exposes the list of supported countries from the config
 */
@Service
public class SupportedCountryService {

    private final Set<String> supportedCountrySet;

    public SupportedCountryService(@Value("${processor.supported.countries}") String supportedCountries) {
        if(supportedCountries == null || supportedCountries.length() == 0) {
            throw new IllegalArgumentException("Supported Countries cannot be empty.");
        }
        this.supportedCountrySet = new HashSet<>();
        this.supportedCountrySet.addAll(Arrays.asList(supportedCountries.split(",\\s?")));
    }

    public boolean supports(String country) {
        return supportedCountrySet.contains(country);
    }
}
