package com.taz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.util.JFRReader;
import org.taz.core.autoencoder.AE;

import java.util.ArrayList;

/**
 * Created by kokulan on 2/22/17.
 */
@Service
public class AEAnalysisPageService {

    @Value("${service.file.path}")
    private String rootPath;

    private JFRReader jfrReader;

    public AEAnalysisPageService() {
        jfrReader = JFRReader.getInstance();
    }

    public void getPageData(String fileName, ModelMap model) {
        String filePath = rootPath + "/" + fileName;
        model.addAttribute("fileName", fileName);

        AE ae = new AE();
        ArrayList<Double> scorelist  = ae.generateScoreSeries(filePath,"App2_gctime");

        StringBuilder graphData = new StringBuilder();
        graphData.append("[");
        int count = 0;
        for(Double entry: scorelist){
            graphData.append("[" + count + "," + entry + "],");
            count++;
        }

        graphData.append("]");

        model.addAttribute("anomalyScore", graphData.toString());

    }

}
