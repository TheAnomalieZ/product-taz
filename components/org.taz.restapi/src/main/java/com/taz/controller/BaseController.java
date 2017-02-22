package com.taz.controller;

import com.taz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {

    @Autowired
    OverviewPageService overviewPageService;

    @Autowired
    JVMInformationPageService jvmInformationPageService;

    @Autowired
    RecordingPageService recordingPageService;

    @Autowired
    FileService fileService;

    @Autowired
    GarbageCollectionPageService garbageCollectionPageService;

    @Autowired
    GCAnalysisPageService gcAnalysisPageService;

    @Autowired
    AEAnalysisPageService aeAnalysisPageService;

    private static final String HOME = "home";
    private static final String ERROR = "error";
    private static final String WELCOME = "welcome";
    private static final String OVERVIEW = "overview";
    private static final String JVM_INFORMATION = "jvmInformation";
    private static final String RECORDING = "recordings";
    private static final String GARBAGE_COLLECTION = "GarbageCollection";
    private static final String CLUSTER_ANALYSIS = "ClusterAnalysis";
    private static final String AE_ANALYSIS = "aeAnalysis";

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
            overviewPageService.getOverviewModel(model, fileName);
        }
        return OVERVIEW;
    }

    @RequestMapping(value = "/jvm_information", method = RequestMethod.GET)
    public String jvmInformationPage(@RequestParam("fileName")String fileName, ModelMap model) {
        jvmInformationPageService.configureJVMInformation(fileName, model);
        return JVM_INFORMATION;
    }

    @RequestMapping(value = "/recording", method = RequestMethod.GET)
    public String recordingPage(@RequestParam("fileName")String fileName, ModelMap model) {
        recordingPageService.configureRecording(fileName, model);
        return RECORDING;
    }

    @RequestMapping(value = "/garbage_collection", method = RequestMethod.GET)
    public String gcPage(@RequestParam("fileName")String fileName, ModelMap model) {
        garbageCollectionPageService.configureGCAttributes(fileName, model);
        return GARBAGE_COLLECTION;
    }

    @RequestMapping(value = "/cluster_analysis", method = RequestMethod.GET)
    public String gcAnalysisPage(@RequestParam("fileName")String fileName, ModelMap model) {
        gcAnalysisPageService.getPageData(fileName, model);
        return CLUSTER_ANALYSIS;
    }

    @RequestMapping(value = "/ae_analysis", method = RequestMethod.GET)
    public String aeAnalysisPage(@RequestParam("fileName")String fileName, ModelMap model) {
        aeAnalysisPageService.getPageData(fileName, model);
        return AE_ANALYSIS;
    }
}