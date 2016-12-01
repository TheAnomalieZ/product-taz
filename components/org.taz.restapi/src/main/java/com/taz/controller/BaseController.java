package com.taz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {

    private static final String VIEW_ANALYZER = "jfr_analyzer";
    private static final String ERROR = "error";
    private static final String WELCOME = "welcome";

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
}