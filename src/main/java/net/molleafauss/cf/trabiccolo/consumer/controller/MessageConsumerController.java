package net.molleafauss.cf.trabiccolo.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.InvalidTradeMessageResponse;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.consumer.service.TradeMessageVerifier;
import net.molleafauss.cf.trabiccolo.processor.MessageProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * This endpoint will consume the messaged received in POST.
 */
@RestController
@Slf4j
public class MessageConsumerController {

    @Autowired
    private MessageProcessorService messageProcessorService;

    @Autowired
    private TradeMessageVerifier tradeMessageVerifier;

    @RequestMapping(value = "/message", method = POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveMessage(@Valid @RequestBody TradeMessage tradeMessage) throws InvalidTradeMessageException {

        tradeMessageVerifier.verify(tradeMessage);

        messageProcessorService.handle(tradeMessage);
        log.debug("Message received: {}", tradeMessage);
    }

    @ExceptionHandler(InvalidTradeMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidTradeMessageResponse handleInvalidMessage(InvalidTradeMessageException ex) {
        return new InvalidTradeMessageResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidTradeMessageResponse handleInvalidMessage(MethodArgumentNotValidException ex) {
        return new InvalidTradeMessageResponse(InvalidTradeMessageException.MISSING_FIELDS);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInvalidMessage(Exception ex) {
    }
}
