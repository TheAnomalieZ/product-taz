package com.taz.service;

import com.taz.data.GCEventModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.parser.events.GCReferenceProcessingEvent;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.MetaspaceSummaryEvent;
import org.taz.commons.parser.models.GCEventsModel;
import org.taz.commons.util.JFRReader;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by K.Kokulan on 12/26/2016.
 */

@Service
public class GarbageCollectionPageService {

    private JFRReader jfrReader;
//    private GCEventsModelDataService gcEventsModelDataService;

    @Autowired
    GCEventModelService gcEventModelService;

    @Value("${service.file.path}")
    private String rootPath;

    public GarbageCollectionPageService() {
        this.jfrReader = JFRReader.getInstance();
    }


    public void getGCModel(String fileName, ModelMap model) {
        String filePath = rootPath + "/" + fileName;

        com.taz.data.entity.GCEventsModel gcEventsModelEntity = gcEventModelService.findGCEventsModelByFileName(fileName);
        GCEventsModel gcEventsModel;

        if(gcEventsModelEntity == null){
            com.taz.data.entity.GCEventsModel gcEventsModel1 = new com.taz.data.entity.GCEventsModel();
            gcEventsModel = jfrReader.getGCEventModel(filePath);
            BeanUtils.copyProperties(gcEventsModel, gcEventsModel1);
            gcEventsModel1.setFileName(fileName);
//            gcEventModelService.save(gcEventsModel1);
        } else {
            GCEventsModel gcEventsModel1 = new GCEventsModel();
            BeanUtils.copyProperties(gcEventsModelEntity, gcEventsModel1);
            gcEventsModel = gcEventsModel1;
        }


        formatDataForGraph(gcEventsModel, model);
        model.addAttribute("gcId", gcEventsModel.getGcIdList());
        HashMap<String, HashMap<String, String>> pieDataHashMap = new HashMap<>();
        HashMap<String, String> pieData = new HashMap<>();
        //sort gc according to the gcid
        Collections.sort(gcEventsModel.getGarbageCollectionEvent(), (o1, o2) -> (Integer.parseInt(o1.getGcId()) - Integer.parseInt(o2.getGcId())));
        Collections.sort(gcEventsModel.getGcIdList(), (o1, o2) -> (Integer.parseInt(o1) - Integer.parseInt(o2)));

        gcEventsModel.getGarbageCollectionEvent().forEach(x -> {
            String longestPause = x.getLongestPause();
            String sumOfPause = x.getSumOfPauses();
            long longestPauseTime = Long.parseLong(longestPause) / 1000;
            long sumOfPauseTime = Long.parseLong(sumOfPause) / 1000;
            long duration = x.getDuration() / 1000;
            x.setLongestPause(String.format("%d s %d ms %d micro s ",
                    TimeUnit.MICROSECONDS.toSeconds(longestPauseTime),
                    TimeUnit.MICROSECONDS.toMillis(longestPauseTime % 1000000),
                    TimeUnit.MICROSECONDS.toMicros(longestPauseTime % 1000)));

            x.setSumOfPauses(String.format("%d s %d ms %d micro s ",
                    TimeUnit.MICROSECONDS.toSeconds(sumOfPauseTime),
                    TimeUnit.MICROSECONDS.toMillis(sumOfPauseTime % 1000000),
                    TimeUnit.MICROSECONDS.toMicros(sumOfPauseTime % 1000)));

            x.setDurationString(String.format("%d s %d ms %d micro s ",
                    TimeUnit.MICROSECONDS.toSeconds(duration),
                    TimeUnit.MICROSECONDS.toMillis(duration % 1000000),
                    TimeUnit.MICROSECONDS.toMicros(duration % 1000)));

            Date startDate = new Date(x.getStartTimestamp() / 1000000);
            x.setStartTimeString(startDate.toString());
            Date endDate = new Date(x.getStartTimestamp() / 1000000);
            x.setEndTimeString(endDate.toString());
        });

        model.addAttribute("garbageCollectionCommon", gcEventsModel.getGarbageCollectionEvent());

        HashMap<String, GCEventsModel> eventsModelHashMap = new HashMap<>();

        gcEventsModel.getGarbageCollectionEvent().forEach(x -> {
            ArrayList<HeapSummaryEvent> heapSummaryEvent = new ArrayList<>();
            ArrayList<GCReferenceProcessingEvent> gcReferenceProcessingEvent = new ArrayList<>();
            ArrayList<MetaspaceSummaryEvent> metaspaceSummaryEvents = new ArrayList<>();
            ArrayList<GarbageCollectionEvent> gcEventList = new ArrayList<>();

            gcEventList.add(x);
            gcEventsModel.getHeapSummaryEvent().forEach(y -> {
                if (y.getGcId().equals(x.getGcId())) {
                    double heapReserved = Double.parseDouble(y.getHeapSpaceReservedSize());
                    double heapCommitted = Double.parseDouble(y.getHeapSpaceCommittedSize());
                    double heapUsed = Double.parseDouble(y.getHeapUsed());

                    y.setHeapSpaceReservedSize(memorySpaceConverter(heapReserved));
                    y.setHeapSpaceCommittedSize(memorySpaceConverter(heapCommitted));
                    y.setHeapUsed(memorySpaceConverter(heapUsed));
                    heapSummaryEvent.add(y);
                }
            });

            gcEventsModel.getGcReferenceProcessingEvent().forEach(event -> {
                if (event.getGcId().equals(x.getGcId())) {
                    gcReferenceProcessingEvent.add(event);
                }
            });

            gcEventsModel.getMetaspaceSummaryEvents().forEach(z -> {
                if (z.getGcId().equals(x.getGcId())) {
                    z.setMetaspaceReserved(memorySpaceConverter(Double.parseDouble(z.getMetaspaceReserved())));
                    z.setMetaspaceUsed(memorySpaceConverter(Double.parseDouble(z.getMetaspaceUsed())));
                    z.setMetaspaceCommitted(memorySpaceConverter(Double.parseDouble(z.getMetaspaceCommitted())));
                    z.setDataSpaceUsed(memorySpaceConverter(Double.parseDouble(z.getDataSpaceUsed())));
                    z.setDataSpaceReserved(memorySpaceConverter(Double.parseDouble(z.getDataSpaceReserved())));
                    z.setDataSpaceCommitted(memorySpaceConverter(Double.parseDouble(z.getDataSpaceCommitted())));
                    z.setClassSpaceCommitted(memorySpaceConverter(Double.parseDouble(z.getClassSpaceCommitted())));
                    z.setClassSpaceReserved(memorySpaceConverter(Double.parseDouble(z.getClassSpaceReserved())));
                    z.setClassSpaceUsed(memorySpaceConverter(Double.parseDouble(z.getClassSpaceUsed())));

                    metaspaceSummaryEvents.add(z);
                }
            });

            GCEventsModel newModel = new GCEventsModel();
            newModel.setGarbageCollectionEvent(gcEventList);
            newModel.setHeapSummaryEvent(heapSummaryEvent);
            newModel.setMetaspaceSummaryEvents(metaspaceSummaryEvents);
            newModel.setGcReferenceProcessingEvent(gcReferenceProcessingEvent);

            eventsModelHashMap.put(x.getGcId(), newModel);

            HashMap<String, String> pieDetails = new HashMap<>();

            newModel.getGcReferenceProcessingEvent().stream().forEach(pie -> {
                pieDetails.put(pie.getType(), pie.getCount());
            });

            String piedata = "[" +
                    "{value:" + pieDetails.get("Soft reference") + ", color:\"#E67A77\"}," +
                    "{value:" + pieDetails.get("Weak reference") + ", color:\"#D9DD81\"}," +
                    "{value:" + pieDetails.get("Final reference") + ", color:\"#79D1CF\"}," +
                    "{value:" + pieDetails.get("Phantom reference") + ", color:\"#BC89D1\"}" +
                    "]";
            pieData.put(x.getGcId(), piedata);
            pieDataHashMap.put(x.getGcId(), pieDetails);
        });

        model.addAttribute("allGCEvents", eventsModelHashMap);
        model.addAttribute("pieChartData", pieDataHashMap);
        model.addAttribute("pieChartDataString", pieData);
        model.addAttribute("referenceObjDataString", getReferenceObjectForGraph(eventsModelHashMap, gcEventsModel.getGcIdList()));
    }

    public void formatDataForGraph(GCEventsModel gcEventsModel, ModelMap model) {
        ArrayList<HeapSummaryEvent> heapSummaryEventArrayList = gcEventsModel.getHeapSummaryEvent();


        if (!heapSummaryEventArrayList.isEmpty()) {
            StringBuilder heapSummaryData = new StringBuilder();
            ArrayList<Double> heapUsedArrayList = new ArrayList<>();
            heapSummaryData.append("[");

            for (HeapSummaryEvent heapSummaryEvent : heapSummaryEventArrayList) {
                double heapUsed = Double.parseDouble(heapSummaryEvent.getHeapUsed()) / (1024 * 1024);
                double committedHeap = Double.parseDouble(heapSummaryEvent.getHeapSpaceCommittedSize()) / (1024 * 1024);
                long time = heapSummaryEvent.getStartTimestamp() / 1000000;

                heapUsedArrayList.add(heapUsed);

                heapSummaryData.append("[" + time + ',' + committedHeap + ',' + heapUsed + "],");
            }
            heapSummaryData.append("]");

            model.addAttribute("heapGraphData", heapSummaryData.toString());
        }
    }

    public String getReferenceObjectForGraph(HashMap<String, GCEventsModel> data, ArrayList<String> gcIdList) {
        StringBuilder referenceModelData = new StringBuilder();
        referenceModelData.append("[");

        for (String gc : gcIdList) {
            long time = 0;
            ArrayList<GCReferenceProcessingEvent> refEventList = new ArrayList<>();
            try{
                time = data.get(gc).getGarbageCollectionEvent().get(0).getStartTimestamp()/1000000;
                refEventList = data.get(gc).getGcReferenceProcessingEvent();
            } catch (NullPointerException e){
                System.out.println(e);
            }
            String SoftReference = "0";
            String WeakReference = "0";
            String FinalReference = "0";
            String PhantomReference = "0";


            for (GCReferenceProcessingEvent x : refEventList) {
                if (x.getType().equals("Soft reference")) {
                    SoftReference = x.getCount();
                } else if (x.getType().equals("Weak reference")) {
                    WeakReference = x.getCount();
                } else if (x.getType().equals("Final reference")) {
                    FinalReference = x.getCount();
                } else if (x.getType().equals("Phantom reference")) {
                    PhantomReference = x.getCount();
                }
            }
            referenceModelData.append("[" + time + "," + SoftReference + "," + WeakReference + "," + FinalReference + "," + PhantomReference + "],");
        }
        referenceModelData.append("]");

        return referenceModelData.toString();
    }

    public void configureGCAttributes(String fileName, ModelMap model) {
        getGCModel(fileName, model);
        model.addAttribute("fileName", fileName);
    }

    public String memorySpaceConverter(double data) {
        String formattedData;
        if (data / (1024 * 1024 * 1024) >= 1) {
            double hr = Math.round(data / (1024 * 1024 * 1024) * 100);
            formattedData = String.valueOf(hr / 100) + " GB";
        } else {
            double hr = Math.round(data / (1024 * 1024) * 100);
            formattedData = String.valueOf(hr / 100) + " MB";
        }

        return formattedData;
    }
}
