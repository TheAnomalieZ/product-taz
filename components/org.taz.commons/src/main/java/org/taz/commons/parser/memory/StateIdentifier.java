package org.taz.commons.parser.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StateIdentifier {
    /**
     * State 0 : Initial state
     * State 1 : HH     //High time between GC,Used heap diff High
     * State 2 : HL     //High time between GC, Used heap diff Low
     * State 3 : LL     //Low time between GC, Used heap diff Low
     * State 4 : LH     //Low time between GC, Used heap diff High
     */
    private final Map<Long, MemEvent> eventMap;
    private List<Integer> stateSequence = new ArrayList<Integer>();

    public StateIdentifier(Map<Long, MemEvent> eventMap) {
        this.eventMap = eventMap;
    }

    public ArrayList<Integer> configureStates() {
        long lastMemoryDif = 0;
        long lastGCTimeDif = -1;
        long tempLastUsedMem = 0;
        long tempLastGCEndTime = -1;
        int mod = 0;
        int count = 1;
        for (Map.Entry<Long, MemEvent> memEventEntry : eventMap.entrySet()) {
            if (count == 1) {
                MemEvent memEvent = memEventEntry.getValue();
                tempLastGCEndTime = memEvent.getStartTimestamp();
                tempLastUsedMem = memEvent.getUsedHeap();

            }else{
                MemEvent memEvent = memEventEntry.getValue();
                long memDif = memEvent.getUsedHeap() - tempLastUsedMem;
                long gcGap = memEvent.getStartTimestamp() - tempLastGCEndTime;
                if(lastGCTimeDif==-1 && lastMemoryDif==0) {
                    lastGCTimeDif = gcGap;
                    lastMemoryDif = memDif;
                }
                else{
                    identifyState(lastMemoryDif,lastGCTimeDif,memDif,gcGap);
                    lastMemoryDif = memDif;
                    lastGCTimeDif = gcGap;
                }
                tempLastGCEndTime = memEvent.getStartTimestamp();
                tempLastUsedMem = memEvent.getUsedHeap();

            }
            count++;
        }

        return (ArrayList<Integer>) stateSequence;
    }

    private void identifyState(long lastMemDiff, long lastGCTimeDiff, long currMemDiff, long currGCTimeDiff) {
        int state = 0;
        if(currGCTimeDiff>=lastGCTimeDiff && currMemDiff>=lastMemDiff) {
            state =1;
            stateSequence.add(0);
        }
        if(currGCTimeDiff>lastGCTimeDiff && currMemDiff<lastMemDiff) {
            state =2;
            stateSequence.add(1);
        }
        if(currGCTimeDiff<lastGCTimeDiff && currMemDiff<lastMemDiff) {
            state =3;
            stateSequence.add(2);
        }
        if(currGCTimeDiff<lastGCTimeDiff && currMemDiff>lastMemDiff) {
            state =4;
            stateSequence.add(3);
        }

    }


}
