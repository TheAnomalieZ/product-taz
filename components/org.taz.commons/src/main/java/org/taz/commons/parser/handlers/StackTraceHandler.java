package org.taz.commons.parser.handlers;

import com.jrockit.mc.common.IMCFrame;
import com.jrockit.mc.common.IMCMethod;
import com.jrockit.mc.flightrecorder.internal.model.FLRStackTrace;
import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

/**
 * Created by K.Kokulan on 2/16/2017.
 */
public class StackTraceHandler {
    private IView view;

    public StackTraceHandler(IView iView) {
        view = iView;
    }

    public LinkedHashMap<ArrayList<String>,Long> getStackTrace(long startTime, long endTime) {
        Map<String, Long> timeCounter = new HashMap<String, Long>();
        LinkedHashMap<String,Long> stackTraceMap = new LinkedHashMap<String, Long>();
        LinkedHashMap<ArrayList<String>,Long> stackMap = new LinkedHashMap<ArrayList<String>, Long>();

        for (IEvent event : view) {

            long stTimeStamp = event.getStartTimestamp() / 1000000;
            long endTimeStamp = event.getEndTimestamp() / 1000000;


            if (startTime <= stTimeStamp && endTime >= endTimeStamp) {
                FLRStackTrace flrStackTrace = (FLRStackTrace) event.getValue("(stackTrace)");
                Stack<String> stack = new Stack<String>();
                ArrayList<String> stackTrace = new ArrayList<>();

                if (flrStackTrace != null) {
                    for (IMCFrame frame : flrStackTrace.getFrames()) {
                        StringBuilder methodBuilder = new StringBuilder();
                        IMCMethod method = frame.getMethod();
                        methodBuilder.append(method.getHumanReadable(false, true, true, true, true, true));
                        if (timeCounter.get(methodBuilder.toString()) == null) {
                            timeCounter.put(methodBuilder.toString(), event.getDuration());
                        } else {
                            Long time = timeCounter.get(methodBuilder.toString());
                            time = time + event.getDuration();
                            timeCounter.put(methodBuilder.toString(), time);
                        }
                        methodBuilder.append(":");
                        methodBuilder.append(event.getEventType().getName());
                        stack.push(methodBuilder.toString());
                        stackTrace.add(methodBuilder.toString());
                    }

                    StringBuilder stackTraceBuilder = new StringBuilder();
                    boolean appendSemicolon = false;
                    while (!stack.empty()) {
                        if (appendSemicolon) {
                            stackTraceBuilder.append(";");
                        } else {
                            appendSemicolon = true;
                        }
                        stackTraceBuilder.append(stack.pop());
                    }
                    String stackStrace = stackTraceBuilder.toString();
                    Long count = stackTraceMap.get(stackStrace);
                    if (count == null) {
                        count = 1L;
                    } else {
                        count++;
                    }
//                    if (count == null) {
//                        count = event.getDuration();
//                    } else {
//                        count+=event.getDuration();
//                    }
                    stackTraceMap.put(stackStrace, count);
                    stackMap.put(stackTrace, count);
                }
            }
        }

        LinkedHashMap<String,Long> orderedMap = sortbyTime(timeCounter);
        return sortbyTime(stackMap);
//        return sortbyTime(stackTraceMap);
//        return orderedMap;
    }

    private LinkedHashMap sortbyTime (Map unorderedMap){
        List<Map.Entry<String,Long>> list =
                new LinkedList<Map.Entry<String, Long>>(unorderedMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });

        LinkedHashMap ordered = new LinkedHashMap();
        for (Map.Entry<String,Long> entry : list){
            ordered.put(entry.getKey(),entry.getValue());
        }

        return ordered;
    }
}
