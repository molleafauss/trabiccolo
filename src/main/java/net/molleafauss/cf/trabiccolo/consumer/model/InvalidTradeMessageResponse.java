package net.molleafauss.cf.trabiccolo.consumer.model;

import lombok.Data;

/**
 * Response returned by the consume endpoint in case of an invalid message received
 */
@Data
public class InvalidTradeMessageResponse {
    private final String error;
}
