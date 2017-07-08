package net.molleafauss.cf.trabiccolo.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Data model of the trade message received by the consumer
 */
@Data
@Builder
@AllArgsConstructor
public class TradeMessage {

    private String userId;

    private String currencyFrom;

    private String currencyTo;

    private Double amountSell;

    private Double amountBuy;

    private Double rate;

    private String timePlaced;

    private String originatingCountry;
}
