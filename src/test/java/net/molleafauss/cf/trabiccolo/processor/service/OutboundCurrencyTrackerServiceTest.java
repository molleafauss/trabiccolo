package net.molleafauss.cf.trabiccolo.processor.service;

import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.molleafauss.cf.trabiccolo.test.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OutboundCurrencyTrackerServiceTest {

    private OutboundCurrencyTrackerService outboundCurrencyTrackerService;

    @Before
    public void setup() {
        outboundCurrencyTrackerService = new OutboundCurrencyTrackerService(testSupportedCountryService(), testSupportedCurrencyService());
    }

    @Test
    public void processSupportedMessage() throws Exception {
        outboundCurrencyTrackerService.processMessage(buildTestMessage().build());

        List<TransferStatistic> transfers = outboundCurrencyTrackerService.listAllRelevantTransfers();
        assertThat(transfers).isNotNull();
        assertThat(transfers.size()).isEqualTo(1);
        TransferStatistic statistic = transfers.get(0);
        assertThat(statistic.getCountry()).isEqualTo("IE");
        assertThat(statistic.getCurrency()).isEqualTo("GBP");
        assertThat(statistic.getCount()).isEqualTo(1);
    }
}