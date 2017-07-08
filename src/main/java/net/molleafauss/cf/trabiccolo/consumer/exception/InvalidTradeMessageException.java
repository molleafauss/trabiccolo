package net.molleafauss.cf.trabiccolo.consumer.exception;

/**
 * Exception thrown if a TradeMessage can't be sent to processing.
 */
public class InvalidTradeMessageException extends Exception {
    public static final String UNSUPPORTED_COUNTRY = "Invalid message: unsupported country";
    public static final String UNSUPPORTED_CURRENCY = "Invalid message: unsupported currency";

    public InvalidTradeMessageException(String message) {
        super(message);
    }
}
