package org.taz.commons.parser.memory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by  Maninesan on 12/12/16.
 */
public class GCPauseTimeSeries {
    private final Map<Long, MemEvent> eventMap;
    private Map<Long,Double> pauseTimeSeries;
    private Map<Long,ArrayList<Double>> heapAndPauseSeries;
    private long recordStartTime;


    public GCPauseTimeSeries(Map<Long, MemEvent> eventMap,long startTime) {
        this.eventMap = eventMap;
        this.recordStartTime = startTime;
        pauseTimeSeries = new LinkedHashMap<Long,Double>();
        heapAndPauseSeries = new LinkedHashMap<Long,ArrayList<Double>>();
    }
/*
    public Map<Long,Double> configureTimeSeries(){
        Map<Long,Double> tempSeries = new LinkedHashMap<Long,Double>();
        double previousTime = recordStartTime;
        long timeCount=1;
        double endTime;
        double startTime;

        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();

            endTime = memEvent.getEndTimestamp();
            startTime = memEvent.getStartTimestamp();

            double previousTimeGap = (startTime-previousTime)/1000000;

            while(previousTimeGap>0){
                tempSeries.put(timeCount++,0d);
                previousTimeGap--;
            }

//            double pauseTime =memEvent.getPauseTime()/1000000;
            double gcTimeGap = (endTime - startTime)/1000000;
            System.out.println(startTime+"-"+previousTime+"="+gcTimeGap);
//            System.out.println("pause Time: "+pauseTime);

            while(gcTimeGap>0){
                tempSeries.put(timeCount++,1d);
                gcTimeGap--;
            }

            previousTime =endTime;
        }

        return tempSeries;
    }
*/
public ArrayList<Short> configureTimeSeries(){
    ArrayList<Short> tempSeries = new  ArrayList<Short> ();
    double previousTime = recordStartTime;
    long timeCount=1;
    double endTime;
    double startTime;

    for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
        MemEvent memEvent = memEventEntry.getValue();

        endTime = memEvent.getEndTimestamp();
        startTime = memEvent.getStartTimestamp();

        double previousTimeGap = (startTime-previousTime)/1000000;

        while(previousTimeGap>0){
            tempSeries.add((short) 0);
            previousTimeGap--;
        }

//            double pauseTime =memEvent.getPauseTime()/1000000;
        double gcTimeGap = (endTime - startTime)/1000000;
//        System.out.println(startTime+"-"+previousTime+"="+gcTimeGap);
//            System.out.println("pause Time: "+pauseTime);

        while(gcTimeGap>0){
            tempSeries.add((short) 1);
            gcTimeGap--;
        }

        previousTime =endTime;
    }

    return tempSeries;
}
/*
    public Map<Long,ArrayList<Double>> heapAndPauseTime(){
        Map<Long,ArrayList<Double>> tempSeries = new LinkedHashMap<Long,ArrayList<Double>>();
        double previousTime = recordStartTime;
        double previousHeap = 0;
        long timeCount=1;
        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();
            double startHeap=memEvent.getStartHeap();
            double endHeap = memEvent.getEndHeap();


            double endTime = memEvent.getEndTimestamp();
            double startTime = memEvent.getStartTimestamp();

            double gcType = memEvent.getType().hashCode() + 0d;

            double heapIncre;


            double previousTimeGap = (startTime-previousTime)/1000000000;
            double previousHeapGap = startHeap-previousHeap;
            double unitHeapIncre = previousHeapGap/previousTimeGap;
            heapIncre =previousHeap;
            while(previousTimeGap>0){
                ArrayList<Double> list= new ArrayList<>();
//                list.add(gcType);
                list.add(heapIncre/1000000);
                heapIncre += unitHeapIncre;
                list.add(0d);
                tempSeries.put(timeCount++,list);
                previousTimeGap--;
            }



            double pauseTime =memEvent.getPauseTime()/1000000;

            double gcTimeGap = (endTime - startTime)/1000000000;
            double gcHeapGap = startHeap-endHeap;
            unitHeapIncre = gcHeapGap/gcTimeGap;
            heapIncre =startHeap;
            while(gcTimeGap>0 && heapIncre>0){
                ArrayList<Double> list= new ArrayList<>();
//                list.add(gcType);
                list.add(heapIncre/1000000);
                heapIncre-= unitHeapIncre;
                list.add(pauseTime);
                tempSeries.put(timeCount++,list);
                gcTimeGap--;
            }

            System.out.println(startTime+"-"+previousTime+"="+gcTimeGap);
            System.out.println("pause Time: "+pauseTime);

            previousHeap = endHeap;
            previousTime =endTime;

        }
        return tempSeries;
    }

*/
    public ArrayList<Double> heapAndPauseTime(){
        ArrayList<Double> tempSeries = new ArrayList<Double>();
        double previousTime = recordStartTime;
        double previousHeap = 0;
        long timeCount=1;
        double startHeap;
        double endHeap;
        double endTime;
        double startTime;
        double heapIncre;
        double previousTimeGap;
        double previousHeapGap;
        double unitHeapIncre;
        double gcHeapGap;
        double gcTimeGap;
        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();
            startHeap=memEvent.getStartHeap();
            endHeap = memEvent.getEndHeap();

            endTime = memEvent.getEndTimestamp();
            startTime = memEvent.getStartTimestamp();


            previousTimeGap = (startTime-previousTime)/1000000;
            previousHeapGap = startHeap-previousHeap;
            unitHeapIncre = previousHeapGap/previousTimeGap;
            heapIncre =previousHeap;
            while(previousTimeGap>0){
                tempSeries.add(heapIncre/1000000);
                heapIncre += unitHeapIncre;
                previousTimeGap--;
            }



            gcTimeGap = (endTime - startTime)/1000000;
            gcHeapGap = startHeap-endHeap;
            unitHeapIncre = gcHeapGap/gcTimeGap;
            heapIncre =startHeap;
            while(gcTimeGap>0 && heapIncre>0){
                tempSeries.add(heapIncre/1000000);
               heapIncre-= unitHeapIncre;
                gcTimeGap--;
            }

            previousHeap = endHeap;
            previousTime =endTime;

        }
        return tempSeries;
    }


}
