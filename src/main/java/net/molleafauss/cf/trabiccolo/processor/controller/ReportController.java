package net.molleafauss.cf.trabiccolo.processor.controller;

import net.molleafauss.cf.trabiccolo.processor.data.TransferStatistic;
import net.molleafauss.cf.trabiccolo.processor.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Returns the list of countries and currency pairs for which message were processed
 */
@RestController
public class ReportController {

    private final StatisticService statisticService;

    @Autowired
    public ReportController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(value = "/rest/statistic", method = RequestMethod.GET)
    public List<TransferStatistic> listTransfers() {
        List<TransferStatistic> result = statisticService.listAllRelevantTransfers();
        return result;
    }
}
