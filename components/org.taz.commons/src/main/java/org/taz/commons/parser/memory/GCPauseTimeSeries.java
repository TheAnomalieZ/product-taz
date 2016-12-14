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

    private double recordEndTime;
    private double endTime;

    public GCPauseTimeSeries(Map<Long, MemEvent> eventMap,long startTime) {
        this.eventMap = eventMap;
        this.recordStartTime = startTime;
        pauseTimeSeries = new LinkedHashMap<Long,Double>();
        heapAndPauseSeries = new LinkedHashMap<Long,ArrayList<Double>>();
    }

    public Map<Long,Double> configureTimeSeries(){
        Map<Long,Double> tempSeries = new LinkedHashMap<Long,Double>();

        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();

            long time = memEvent.getStartTimestamp()-recordStartTime;
            time= Math.round(time/1000000000);
            double pauseTime =memEvent.getPauseTime()/1000000;

            double tempPauseTime = pauseTime;
            if(time==0)
                while(tempPauseTime>0){
                    time+=1;
                    tempPauseTime-=1000;
                }

            tempSeries.put(time,pauseTime);
            endTime = time+pauseTime/1000;
            System.out.println(memEvent.getStartTimestamp()+"-"+recordStartTime+"="+time);
            System.out.println("pause Time: "+pauseTime);
        }


        long i=1;
        while(i<=endTime){
            try{
                double tempPauseTime = tempSeries.get(i);

                pauseTimeSeries.put(i++, tempPauseTime);

                double secondTemp=Math.round(tempPauseTime/1000);

                for(int j=1;j<secondTemp;j++){
                    pauseTimeSeries.put(i++, tempPauseTime);
                }
            }catch(NullPointerException ex){
                pauseTimeSeries.put(i++,0d);
            }
        }

        return pauseTimeSeries;
    }
    


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

            double heapIncre;


            double previousTimeGap = (startTime-previousTime)/1000000000;
            double previousHeapGap = startHeap-previousHeap;
            double unitHeapIncre = previousHeapGap/previousTimeGap;
            heapIncre =previousHeap;
            while(previousTimeGap>0){
                ArrayList<Double> list= new ArrayList<>();
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


}
