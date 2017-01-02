package com.taz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.parser.events.RecordingSettingEvent;
import org.taz.commons.util.JFRReader;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by K.Kokulan on 12/26/2016.
 */

@Service
public class RecordingPageService {
    private JFRReader jfrReader;

    @Value("${service.file.path}")
    private String rootPath;

    public RecordingPageService() {
        this.jfrReader = JFRReader.getInstance();
    }

    public ArrayList<RecordingSettingEvent> getRecordings(String fileName) {
        String filePath = rootPath + "/" + fileName;
        ArrayList<RecordingSettingEvent> recordingSettingEvents = jfrReader.getRecordingSettingList(filePath);
        ArrayList<RecordingSettingEvent> recordingSettingEventsList = new ArrayList<>();
        ArrayList<String> eventNames = new ArrayList<>();

        recordingSettingEvents.parallelStream().forEach(x -> {
            if (!eventNames.contains(x.getName())) {
                eventNames.add(x.getName());

                if (Double.parseDouble(x.getPeriod())<0) {
                    x.setPeriod("N/A");
                } else if (Double.parseDouble(x.getPeriod()) == 0) {
                    x.setPeriod("0s");
                } else if (Double.parseDouble(x.getPeriod()) / 1000000 < 1000) {
                    x.setPeriod(Double.parseDouble(x.getPeriod()) / 1000000 + "ms");
                } else {
                    x.setPeriod(Double.parseDouble(x.getPeriod()) / 1000000000 + "s");
                }

                if (Double.parseDouble(x.getThreshold())<0) {
                    x.setThreshold("N/A");
                } else {
                    x.setThreshold(Double.parseDouble(x.getThreshold()) / 1000000 + "ms");
                }

                recordingSettingEventsList.add(x);
            }
        });
        return recordingSettingEventsList;
    }

    public void configureRecording(String fileName, ModelMap model) {
        model.addAttribute("recording", getRecordings(fileName));
        model.addAttribute("fileName", fileName);
    }
}
