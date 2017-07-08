package net.molleafauss.cf.trabiccolo.processor.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class TransferStatistic {
    @NonNull
    private final String country;
    @NonNull
    private final String currency;
    private final int count;
}
