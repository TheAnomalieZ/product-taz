package com.taz.controller;

import com.taz.service.FileService;
import com.taz.service.GraphDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {

    @Autowired
    GraphDataService graphDataService;

    @Autowired
    FileService fileService;

    private static final String HOME = "home";
    private static final String ERROR = "error";
    private static final String WELCOME = "welcome";
    private static final String OVERVIEW = "overview";

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String jfrAnalyzer(ModelMap model) {
        model.addAttribute("chart_title", "Memory Event");
        model.addAttribute("AvailableFileNames", fileService.getAllFileNames());
        return HOME;
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
    public String overviewPage(@RequestParam("fileName")String fileName, ModelMap model) {
        if(!fileName.isEmpty()){
            graphDataService.getOverviewModel(model, fileName);
        }
        return OVERVIEW;
    }
}