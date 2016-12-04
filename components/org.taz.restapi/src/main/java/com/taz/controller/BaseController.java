package com.taz.controller;

import com.taz.service.GraphDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {

    @Autowired
    GraphDataService graphDataService;

    private static final String VIEW_ANALYZER = "jfr_analyzer";
    private static final String ERROR = "error";
    private static final String WELCOME = "welcome";
    private static final String OVERVIEW = "overview";

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String jfrAnalyzer(ModelMap model) {
        model.addAttribute("chart_title", "Memory Event");
        return VIEW_ANALYZER;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(ModelMap model) {
        return ERROR;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage(ModelMap model) {
        return WELCOME;
    }

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public String overviewPage(ModelMap model) {
        model.addAttribute("heapUsageData", graphDataService.getHeapUsageData());
        model.addAttribute("totalCpuUsage", graphDataService.getCpuUsageData());
        model.addAttribute("gcData", graphDataService.getGcData());

        return OVERVIEW;
    }
}