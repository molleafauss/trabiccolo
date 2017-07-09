package net.molleafauss.cf.trabiccolo.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException;
import net.molleafauss.cf.trabiccolo.consumer.model.InvalidTradeMessageResponse;
import net.molleafauss.cf.trabiccolo.consumer.model.TradeMessage;
import net.molleafauss.cf.trabiccolo.consumer.service.TradeMessageVerifier;
import net.molleafauss.cf.trabiccolo.processor.MessageProcessorService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.INVALID_DATE;
import static net.molleafauss.cf.trabiccolo.consumer.exception.InvalidTradeMessageException.MISSING_FIELDS;
import static net.molleafauss.cf.trabiccolo.test.TestHelper.buildTestMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MessageConsumerController.class)
public class MessageConsumerControllerITest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TradeMessageVerifier tradeMessageVerifier;
    @MockBean
    private MessageProcessorService messageProcessorService;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertThat(tradeMessageVerifier).isNotNull();
        assertThat(messageProcessorService).isNotNull();
    }

    private ResultActions postMessage(TradeMessage message) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(message)));
    }

    @Test
    public void properMessageWillReturnAccepted() throws Exception {
        TradeMessage message = buildTestMessage().build();

        postMessage(message)
                .andExpect(status().isAccepted())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    public void messageFailVerificationWillReturnBadRequest() throws Exception {
        doThrow(new InvalidTradeMessageException(INVALID_DATE))
                .when(tradeMessageVerifier).verify(any(TradeMessage.class));
        TradeMessage message = buildTestMessage().build();

        postMessage(message)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(INVALID_DATE)));
    }

    @Test
    public void messageMissingFieldWillReturnBadRequest() throws Exception {
        TradeMessage message = buildTestMessage().originatingCountry(null).build();

        postMessage(message)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MISSING_FIELDS)));
    }

    @Test
    public void bogusMessageWillReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/message")
                .contentType(MediaType.TEXT_PLAIN)
                .content("bogus"))
            .andExpect(status().isUnsupportedMediaType())
            .andExpect(content().string(""));
    }
}