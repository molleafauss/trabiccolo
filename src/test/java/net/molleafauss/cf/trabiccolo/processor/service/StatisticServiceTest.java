package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.molleafauss.cf.trabiccolo.test.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StatisticServiceTest {

    private StatisticService statisticService;

    @Before
    public void setup() {
        statisticService = new StatisticService(testSupportedCountryService(), testSupportedCurrencyService());
    }

    @Test
    public void processSupportedMessage() throws Exception {
        statisticService.processMessage(buildTestMessage().build());

        List<TransferStatistic> transfers = statisticService.listAllRelevantTransfers();
        assertThat(transfers).isNotNull();
        assertThat(transfers.size()).isEqualTo(1);
        TransferStatistic statistic = transfers.get(0);
        assertThat(statistic.getCountry()).isEqualTo("IE");
        assertThat(statistic.getCurrencyFrom()).isEqualTo("EUR");
        assertThat(statistic.getCurrencyTo()).isEqualTo("GBP");
        assertThat(statistic.getCount()).isEqualTo(1);
    }
}