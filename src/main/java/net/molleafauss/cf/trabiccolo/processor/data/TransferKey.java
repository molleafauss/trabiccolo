package net.molleafauss.cf.trabiccolo.processor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Triple (country, currencyFrom, currencyTo) that represents a transfer processed.
 */
@Data
public class TransferKey {
    @NonNull
    private final String country;
    @NonNull
    private final String currencyFrom;
    @NonNull
    private final String currencyTo;
}
