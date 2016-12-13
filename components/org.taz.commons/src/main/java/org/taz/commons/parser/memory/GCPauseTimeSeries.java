package org.taz.commons.parser.memory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by  Maninesan on 12/12/16.
 */
public class GCPauseTimeSeries {
    private final Map<Long, MemEvent> eventMap;
    private Map<Long,Long> pauseTimeSeries;
    private long startTime;
    private long endTime;

    public GCPauseTimeSeries(Map<Long, MemEvent> eventMap,long startTime) {
        this.eventMap = eventMap;
        this.startTime = startTime;
        pauseTimeSeries = new LinkedHashMap<Long,Long>();
    }

    public Map<Long,Long> configureTimeSeries(){
        Map<Long,Long> tempSeries = new LinkedHashMap<Long,Long>();
        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            MemEvent memEvent = memEventEntry.getValue();

            long time = (memEvent.getStartTimestamp()-startTime)/1000000;
            long pauseTime =memEvent.getPauseTime()/1000;
            tempSeries.put(time,pauseTime);
            endTime = time;
            System.out.println(memEvent.getStartTimestamp()+"-"+startTime+"="+time);
            System.out.println("pause Time: "+pauseTime);
        }


        long i=1;
        while(i<=endTime){
            try{
                long tempPauseTime = tempSeries.get(i);
                for (int j = 0; j < tempPauseTime; j++) {
                    pauseTimeSeries.put(i++, tempPauseTime);
                }

            }catch(NullPointerException ex){
                pauseTimeSeries.put(i++,0L);
            }
        }

        return pauseTimeSeries;
    }

}
