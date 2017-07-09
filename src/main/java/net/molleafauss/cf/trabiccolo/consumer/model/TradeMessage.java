package net.molleafauss.cf.trabiccolo.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data model of the trade message received by the consumer
 */
@Data
@Builder
@AllArgsConstructor
public class TradeMessage {
    @NotNull
    private String userId;
    @NotNull
    private String currencyFrom;
    @NotNull
    private String currencyTo;
    @NotNull
    private Double amountSell;
    @NotNull
    private Double amountBuy;
    @NotNull
    private Double rate;
    @NotNull
    private String timePlaced;
    @NotNull
    private String originatingCountry;
}
