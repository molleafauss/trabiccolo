package net.molleafauss.cf.trabiccolo.consumer.controller;

import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.consumer.service.ConsumerMetricsService;
import net.molleafauss.cf.trabiccolo.consumer.service.TradeMessageVerifier;
import net.molleafauss.cf.trabiccolo.processor.MessageProcessorService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static net.molleafauss.cf.trabiccolo.test.TestHelper.buildTestMessage;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class MessageConsumerControllerTest {

    @InjectMocks
    private MessageConsumerController messageConsumerController;

    @Mock
    private MessageProcessorService messageProcessorService;

    @Mock
    private TradeMessageVerifier tradeMessageVerifier;

    @Mock
    private ConsumerMetricsService consumerMetricsService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void receiveMessageWithCorrectMessageWontFail() throws Exception {
        TradeMessage message = buildTestMessage().build();

        messageConsumerController.receiveMessage(message);
    }

    @Test
    public void receiveMessageWhichFailsValidationWillRaiseException() throws Exception {
        TradeMessage message = buildTestMessage().build();
        doThrow(InvalidTradeMessageException.class).when(tradeMessageVerifier).verify(message);

        exception.expect(InvalidTradeMessageException.class);

        messageConsumerController.receiveMessage(message);
    }
}