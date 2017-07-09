package net.molleafauss.cf.trabiccolo.processor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.CounterService;

import javax.lang.model.util.Types;

import static net.molleafauss.cf.trabiccolo.test.TestHelper.buildTestMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyTrackerMetricServiceTest {

    @InjectMocks
    private CurrencyTrackerMetricService currencyTrackerMetricService;

    @Mock
    private CounterService counterService;

    @Test
    public void validMessageWillGenerate3Metrics() throws Exception {
        currencyTrackerMetricService.processMessage(buildTestMessage().build());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(counterService, times(3)).increment(captor.capture());

        assertThat(captor.getAllValues().get(0)).isEqualTo("trabiccolo.processor.message.originatingCountry.IE");
        assertThat(captor.getAllValues().get(1)).isEqualTo("trabiccolo.processor.message.currencyFrom.EUR.IE");
        assertThat(captor.getAllValues().get(2)).isEqualTo("trabiccolo.processor.message.currencyTo.GBP.IE");
    }

    @Test
    public void messageWithNoCountryWillNotGenerateMetrics() throws Exception {
        currencyTrackerMetricService.processMessage(buildTestMessage().originatingCountry(null).build());

        verify(counterService, times(0)).increment(anyString());
    }

    @Test
    public void messageWithNoCurrencyFromWillNotGenerateMetrics() throws Exception {
        currencyTrackerMetricService.processMessage(buildTestMessage().currencyFrom(null).build());

        verify(counterService, times(0)).increment(anyString());
    }

    @Test
    public void messageWithNoCurrencyToWillNotGenerateMetrics() throws Exception {
        currencyTrackerMetricService.processMessage(buildTestMessage().currencyTo(null).build());

        verify(counterService, times(0)).increment(anyString());
    }
}