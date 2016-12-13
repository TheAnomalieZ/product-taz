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
    private long startTime;
    private double endTime;
    private long duration;

    public GCPauseTimeSeries(Map<Long, MemEvent> eventMap,long startTime,long duration) {
        this.eventMap = eventMap;
        this.startTime = startTime;
        this.duration = duration;
        pauseTimeSeries = new LinkedHashMap<Long,Double>();
    }

    public Map<Long,Double> configureTimeSeries(){
        Map<Long,Double> tempSeries = new LinkedHashMap<Long,Double>();

        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();

            long time = memEvent.getStartTimestamp()-startTime;
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
            System.out.println(memEvent.getStartTimestamp()+"-"+startTime+"="+time);
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

}
