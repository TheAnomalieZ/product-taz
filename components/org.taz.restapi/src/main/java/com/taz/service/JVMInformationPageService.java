package com.taz.service;

import com.taz.graph_models.JVMInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.parser.events.InitialSystemPropertyEvent;
import org.taz.commons.parser.events.JVMInformationEvent;
import org.taz.commons.util.JFRReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by K.Kokulan on 12/25/2016.
 */

@Service
public class JVMInformationPageService {
    private JFRReader jfrReader;

    @Value("${service.file.path}")
    private String rootPath;

    public JVMInformationPageService() {
        jfrReader = JFRReader.getInstance();
    }

    public JVMInformation getJVMInformation(String fileName) {
        String filePath = rootPath + "/" + fileName;

        JVMInformationEvent jvmInformationEvent = jfrReader.getJVMInformationEventDashboard(filePath);
        JVMInformation   jvmInformation = new JVMInformation();
        Date d = new Date(Long.parseLong(jvmInformationEvent.getJvmStartTime())/1000000);
        jvmInformation.setJvmStartTime(d.toString());
        jvmInformation.setJvmVersion(jvmInformationEvent.getJvmVersion());
        jvmInformation.setJavaArguments(jvmInformationEvent.getJavaArguments());
        jvmInformation.setJvmArguments(jvmInformationEvent.getJvmArguments());
        jvmInformation.setJvmName(jvmInformationEvent.getJvmName());
        return jvmInformation;
    }

    public HashMap<String, String> getInitialSystemProperty(String fileName) {
        String filePath = rootPath + "/" + fileName;

        ArrayList<InitialSystemPropertyEvent> initialSystemPropertyEvents = jfrReader.getInitSystemPropertyEventList(filePath);
        HashMap<String,String> initialSystemPropertyMap = new HashMap<>();

        initialSystemPropertyEvents.parallelStream().forEach(x -> {
            if(!initialSystemPropertyMap.containsKey(x.getKey())){
                initialSystemPropertyMap.put(x.getKey(), x.getValue());
            }
        });

        return initialSystemPropertyMap;
    }

    public void configureJVMInformation(String fileName, ModelMap model) {
        model.addAttribute("jvmInformation", getJVMInformation(fileName));
        model.addAttribute("initialSystemProperties", getInitialSystemProperty(fileName));
        model.addAttribute("fileName",fileName);
    }
}
