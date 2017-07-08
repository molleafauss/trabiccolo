package net.molleafauss.cf.trabiccolo.processor.controller;

import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import net.molleafauss.cf.trabiccolo.processor.service.OutboundCurrencyTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Returns the list of countries and currency pairs for which message were processed
 */
@RestController
public class OutboundCurrencyReportController {

    private final OutboundCurrencyTrackerService outboundCurrencyTrackerService;

    @Autowired
    public OutboundCurrencyReportController(OutboundCurrencyTrackerService outboundCurrencyTrackerService) {
        this.outboundCurrencyTrackerService = outboundCurrencyTrackerService;
    }

    @RequestMapping(value = "/report/outbound", method = RequestMethod.GET)
    public List<TransferStatistic> listTransfers() {
        return outboundCurrencyTrackerService.listAllRelevantTransfers();
    }
}
