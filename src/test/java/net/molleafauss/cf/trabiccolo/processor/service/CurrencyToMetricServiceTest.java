package net.molleafauss.cf.trabiccolo.processor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.CounterService;

import static net.molleafauss.cf.trabiccolo.test.TestHelper.buildTestMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyToMetricServiceTest {

    @InjectMocks
    private CurrencyToMetricService currencyToMetricService;

    @Mock
    private CounterService counterService;

    @Test
    public void validMessageWillGenerateMetrics() throws Exception {
        currencyToMetricService.processMessage(buildTestMessage().build());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(counterService).increment(captor.capture());

        assertThat(captor.getValue()).isEqualTo("trabiccolo.processor.message.currencyTo.GBP.IE");
    }

    @Test
    public void messageWithNoCountryWillNotGenerateMetrics() throws Exception {
        currencyToMetricService.processMessage(buildTestMessage().originatingCountry(null).build());

        verify(counterService, times(0)).increment(anyString());
    }

    @Test
    public void messageWithNoCurrencyToWillNotGenerateMetrics() throws Exception {
        currencyToMetricService.processMessage(buildTestMessage().currencyTo(null).build());

        verify(counterService, times(0)).increment(anyString());
    }
}