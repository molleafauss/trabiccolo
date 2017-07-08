package net.molleafauss.cf.trabiccolo.processor.controller;

import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import net.molleafauss.cf.trabiccolo.processor.service.InboundCurrencyTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Returns the list of countries and currency pairs for which message were processed
 */
@RestController
public class InboundCurrencyReportController {

    private final InboundCurrencyTrackerService inboundCurrencyTrackerService;

    @Autowired
    public InboundCurrencyReportController(InboundCurrencyTrackerService inboundCurrencyTrackerService) {
        this.inboundCurrencyTrackerService = inboundCurrencyTrackerService;
    }

    @RequestMapping(value = "/report/inbound", method = RequestMethod.GET)
    public List<TransferStatistic> listTransfers() {
        return inboundCurrencyTrackerService.listAllRelevantTransfers();
    }
}
