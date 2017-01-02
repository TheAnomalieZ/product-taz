package com.taz.service;

import com.taz.graph_models.CpuUsage;
import com.taz.graph_models.GcData;
import com.taz.graph_models.HeapUsage;
import com.taz.graph_models.JVMInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.constants.TAZConstants;
import org.taz.commons.parser.events.JVMInformationEvent;
import org.taz.commons.parser.events.CPULoadEvent;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.util.JFRReader;

import java.util.*;

/**
 * Created by K.Kokulan on 12/3/2016.
 */

@Service
public class OverviewPageService {
    private JFRReader jfrReader;

    @Value("${service.file.path}")
    private String rootPath;

    public OverviewPageService() {
        jfrReader = JFRReader.getInstance();
    }

    public void getOverviewModel(ModelMap model, String name) {
        String filePath = rootPath + "/" + name;
        HashMap<String, Object> overviewDataMap = jfrReader.getEventsForOverviewPage(filePath);

        ArrayList<HeapSummaryEvent> heapSummaryEvents = (ArrayList<HeapSummaryEvent>) overviewDataMap.get(TAZConstants.HEAP_SUMMARY_EVENT);
        ArrayList<CPULoadEvent> cpuLoadEventsList = (ArrayList<CPULoadEvent>) overviewDataMap.get(TAZConstants.CPU_LOAD_EVENT);
        ArrayList<GarbageCollectionEvent> garbageCollectionEvents = (ArrayList<GarbageCollectionEvent>)overviewDataMap.get(TAZConstants.GC_EVENT);
        JVMInformationEvent jvmInformationEvent = (JVMInformationEvent) overviewDataMap.get(TAZConstants.JVM_INFORMATION);

        model.addAttribute("totalCpuUsage", getCpuUsageData(cpuLoadEventsList));
        model.addAttribute("heapUsageData", getHeapUsageData(heapSummaryEvents));
        model.addAttribute("jvmInformation", getJVMInformation(jvmInformationEvent));
        model.addAttribute("gcData", getGcData(garbageCollectionEvents));
        model.addAttribute("fileName",name);
    }

    public HeapUsage getHeapUsageData(ArrayList<HeapSummaryEvent> heapSummaryEvents) {
//        String filePath = rootPath + "/" + fileName;
        HeapUsage heapUsage = new HeapUsage();
//        ArrayList<HeapSummaryEvent> heapSummaryEventArrayList = jfrReader.getHeapSummaryDashboard(filePath);
        ArrayList<HeapSummaryEvent> heapSummaryEventArrayList = heapSummaryEvents;

        if (!heapSummaryEventArrayList.isEmpty()) {
            StringBuilder heapSummaryData = new StringBuilder();
            ArrayList<Double> heapUsedArrayList = new ArrayList<>();
            double sumOfHeapUsed = 0d;
            heapSummaryData.append("[");

            for (HeapSummaryEvent heapSummaryEvent : heapSummaryEventArrayList) {
                double heapUsed = Double.parseDouble(heapSummaryEvent.getHeapUsed()) / (1024 * 1024);
                double committedHeap = Double.parseDouble(heapSummaryEvent.getHeapSpaceCommittedSize()) / (1024 * 1024);
//                long time = (heapSummaryEvent.getStartTimestamp() + heapSummaryEvent.getEndTimestamp())/2000000;
                long time = heapSummaryEvent.getStartTimestamp() / 1000000;

                sumOfHeapUsed += heapUsed;
                heapUsedArrayList.add(heapUsed);

                heapSummaryData.append("[" + time + ',' + committedHeap + ',' + heapUsed + "],");
            }
            heapSummaryData.append("]");

            double max = Collections.max(heapUsedArrayList);

            double tenthPower = Math.floor(Math.log10(max));
            double place = Math.pow(10, tenthPower);

            heapUsage.setGaugeMax((int) place * 10);
            heapUsage.setMax(max);
            heapUsage.setAvg(sumOfHeapUsed / heapUsedArrayList.size());
            heapUsage.setData(max);
            heapUsage.setTenpData(heapSummaryData.toString());
        }

        return heapUsage;
    }

    public CpuUsage getCpuUsageData( ArrayList<CPULoadEvent> cpuLoadEventsList) {
//        String filePath = rootPath + "/" + fileName;
        CpuUsage cpuUsage = new CpuUsage();
//        ArrayList<CPULoadEvent> cpuLoadEvents = jfrReader.getCPUEventsDashboard(filePath);
        ArrayList<CPULoadEvent> cpuLoadEvents = cpuLoadEventsList;

        if (!cpuLoadEvents.isEmpty()) {
            double sumOfMachineTotal = 0d;
            StringBuilder cpuData = new StringBuilder();
            ArrayList<Double> machineTotalList = new ArrayList<>();

            cpuData.append("[");
            for (CPULoadEvent iterator : cpuLoadEvents) {
                long time = (iterator.getStartTimestamp() + iterator.getEndTimestamp()) / 2000000;
                double machineTotal = Double.parseDouble(iterator.getMachineTotal()) * 100;
                double jvmAppAndUser = Double.parseDouble(iterator.getJvmUser()) * 100;
                double jvmSystem = Double.parseDouble(iterator.getJvmUser()) * 100;
                sumOfMachineTotal += machineTotal;
                machineTotalList.add(machineTotal);

                cpuData.append("[" + time + ',' + machineTotal + ',' + jvmAppAndUser + "," + jvmSystem + "],");
            }
            cpuData.append("]");
            double maxCpu = Collections.max(machineTotalList);
            double avg = sumOfMachineTotal / cpuLoadEvents.size();
            cpuUsage.setMax(maxCpu);
            cpuUsage.setAvg(avg);
            cpuUsage.setData(maxCpu);
            cpuUsage.setCpuUsageData(cpuData.toString());
        }

        return cpuUsage;
    }

    public GcData getGcData(ArrayList<GarbageCollectionEvent> garbageCollectionEvents) {
//        String filePath = rootPath + "/" + fileName;
//        ArrayList<GarbageCollectionEvent> garbageCollectionEventArrayList = jfrReader.getGarbageCollectionEventDashboard(filePath);
        ArrayList<GarbageCollectionEvent> garbageCollectionEventArrayList = garbageCollectionEvents;

        ArrayList<Double> longestPausesList = new ArrayList<>();
        double totalSmOfPauses = 0d;
        for (GarbageCollectionEvent garbageCollectionEvent : garbageCollectionEventArrayList) {
            longestPausesList.add(Double.parseDouble(garbageCollectionEvent.getLongestPause()));
            totalSmOfPauses += Double.parseDouble(garbageCollectionEvent.getSumOfPauses());
        }

        double max = Collections.max(longestPausesList);
        double avg = totalSmOfPauses / longestPausesList.size();
        double tenthPower = Math.floor(Math.log10(max / 1000000));
        double place = Math.pow(10, tenthPower);

        GcData gcData = new GcData();
        gcData.setGaugeMax((int) place * 10);
        gcData.setMax(max / 1000000);
        gcData.setAvg(avg / 1000000);
        gcData.setData(max / 1000000);

        return gcData;
    }

    public JVMInformation getJVMInformation(JVMInformationEvent jvmInformationEvent) {
//        String filePath = rootPath + "/" + fileName;
        JVMInformation jvmInformation = new JVMInformation();
        Date d = new Date(Long.parseLong(jvmInformationEvent.getJvmStartTime())/1000000);
        jvmInformation.setJvmStartTime(d.toString());
        jvmInformation.setJvmVersion(jvmInformationEvent.getJvmVersion());
        return jvmInformation;
    }
}
