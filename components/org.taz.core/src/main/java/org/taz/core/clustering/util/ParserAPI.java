package org.taz.core.clustering.util;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IEventType;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.exceptions.AttributeNotFoundException;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vithulan on 1/16/17.
 */
public class ParserAPI {
    JFRParserV18 jfrParser;

    public ParserAPI(String FILE_PATH) {
        jfrParser = new JFRParserV18(FILE_PATH);
    }

    public void getAttributes() {
        ArrayList<EventNode> eventNodes = jfrParser.getAllJFRAttributes();
        for (EventNode eventNode : eventNodes) {
            try {
                Map<String, ArrayList<Object>> eventMap = jfrParser.getAttributeValues(eventNode.getEventName(), new ArrayList<String>(eventNode.getAttributes()));
                System.out.println("=====" + eventNode.getEventName() + "=======");
                for (Map.Entry<String, ArrayList<Object>> entry : eventMap.entrySet()) {
                    System.out.println(entry.getKey());
                    for (Object value : entry.getValue()) {
                        System.out.println(value.toString());
                    }
                }
            } catch (EventNotFoundException e) {
                e.printStackTrace();
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generate attribute table
     * @return Treemap of GCID and Parameter object with anomaly score
     */
    public TreeMap<Long, Parameter> generateAttributeTable() {
        System.out.println("Generating attribute table..");
        Map<Long, Parameter> parametersMap = new HashMap<>();
        IView iView = jfrParser.getIView();
        for (IEvent event : iView) {
            IEventType iEventType = event.getEventType();
            switch (iEventType.getName()) {
                case "Heap Summary":
                    long gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setHeapUsed(((Number) event.getValue(JFRConstants.HEAPUSED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        } else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setHeapUsed(((Number) event.getValue(JFRConstants.HEAPUSED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                        //System.out.println(gcId);
                    } /*else if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.AFTERGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                        else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);

                        }
                    }*/
                    break;
                case "GC Phase Pause":
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (parametersMap.containsKey(gcId)) {
                        Parameter parameter = parametersMap.get(gcId);
                        parameter.setgCPauseDuration(((Number) event.getValue(JFRConstants.DURATION)).longValue());
                        parametersMap.put(gcId, parameter);
                    } else {
                        Parameter parameter = new Parameter();
                        parameter.setGcID(gcId);
                        parameter.setgCPauseDuration(((Number) event.getValue(JFRConstants.DURATION)).longValue());
                        parametersMap.put(gcId, parameter);
                    }
                    break;
                case "Parallel Scavenge Heap Summary":
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setOldObjectSpaceUsed(((Number) event.getValue(JFRConstants.OLDOBJECTSPACEUSED)).longValue());
                            parametersMap.put(gcId, parameter);
                        } else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setOldObjectSpaceUsed(((Number) event.getValue(JFRConstants.OLDOBJECTSPACEUSED)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                    }
                    break;
                case "Garbage Collection" :
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (parametersMap.containsKey(gcId)) {
                        Parameter parameter = parametersMap.get(gcId);
                        parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                        parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                        parametersMap.put(gcId, parameter);
                    } else {
                        Parameter parameter = new Parameter();
                        parameter.setGcID(gcId);
                        parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                        parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                        parametersMap.put(gcId, parameter);
                    }

            }
            // System.out.println(iEventType.getName());
        }
        TreeMap<Long, Parameter> parameterTreeMap = new TreeMap<>();
        parameterTreeMap.putAll(parametersMap);

        /*for (Map.Entry entry : parameterTreeMap.entrySet()) {
            Parameter parameter = (Parameter) entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("HeapUsed : " + parameter.getHeapUsed());
            System.out.println("Start : " + parameter.getStartTime());
            System.out.println("end : " + parameter.getEndTime());
            System.out.println("pause : " + parameter.getgCPauseDuration());
            System.out.println("old : " + parameter.getOldObjectSpaceUsed());

        }*/
        return parameterTreeMap;
    }

    public TreeMap<Long, Parameter> generateAttributeTableForTest() {
        System.out.println("Generating attribute table..");
        Map<Long, Parameter> parametersMap = new HashMap<>();
        IView iView = jfrParser.getIView();
        for (IEvent event : iView) {
            IEventType iEventType = event.getEventType();
            switch (iEventType.getName()) {
                case "Heap Summary":
                    long gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setHeapUsed(((Number) event.getValue(JFRConstants.HEAPUSED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        } else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setHeapUsed(((Number) event.getValue(JFRConstants.HEAPUSED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                        //System.out.println(gcId);
                    } /*else if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.AFTERGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                        else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);

                        }
                    }*/
                    break;
                case "GC Phase Pause":
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (parametersMap.containsKey(gcId)) {
                        Parameter parameter = parametersMap.get(gcId);
                        parameter.setgCPauseDuration(((Number) event.getValue(JFRConstants.DURATION)).longValue());
                        parametersMap.put(gcId, parameter);
                    } else {
                        Parameter parameter = new Parameter();
                        parameter.setGcID(gcId);
                        parameter.setgCPauseDuration(((Number) event.getValue(JFRConstants.DURATION)).longValue());
                        parametersMap.put(gcId, parameter);
                    }
                    break;
                case "Parallel Scavenge Heap Summary":
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setOldObjectSpaceUsed(((Number) event.getValue(JFRConstants.OLDOBJECTSPACEUSED)).longValue());
                            parametersMap.put(gcId, parameter);
                        } else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setOldObjectSpaceUsed(((Number) event.getValue(JFRConstants.OLDOBJECTSPACEUSED)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                    }
                    break;
                case "Garbage Collection" :
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (parametersMap.containsKey(gcId)) {
                        Parameter parameter = parametersMap.get(gcId);
                        parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                        parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                        parametersMap.put(gcId, parameter);
                    } else {
                        Parameter parameter = new Parameter();
                        parameter.setGcID(gcId);
                        parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                        parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                        parametersMap.put(gcId, parameter);
                    }
                    break;

                case "Metaspace Summary" :
                    gcId = ((Number) event.getValue(JFRConstants.GCID)).longValue();
                    if (event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)) {
                        if (parametersMap.containsKey(gcId)) {
                            Parameter parameter = parametersMap.get(gcId);
                            parameter.setMetaspaceUsed(((Number) event.getValue(JFRConstants.METASPACE_USED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        } else {
                            Parameter parameter = new Parameter();
                            parameter.setGcID(gcId);
                            parameter.setMetaspaceUsed(((Number) event.getValue(JFRConstants.METASPACE_USED)).longValue());
                            //parameter.setStartTime(((Number) event.getValue(JFRConstants.STARTTIME)).longValue());
                            //parameter.setEndTime(((Number) event.getValue(JFRConstants.ENDTIME)).longValue());
                            parametersMap.put(gcId, parameter);
                        }
                        //System.out.println(gcId);
                    }
                    break;
            }
            // System.out.println(iEventType.getName());
        }
        TreeMap<Long, Parameter> parameterTreeMap = new TreeMap<>();
        parameterTreeMap.putAll(parametersMap);

        /*for (Map.Entry entry : parameterTreeMap.entrySet()) {
            Parameter parameter = (Parameter) entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("HeapUsed : " + parameter.getHeapUsed());
            System.out.println("Start : " + parameter.getStartTime());
            System.out.println("end : " + parameter.getEndTime());
            System.out.println("pause : " + parameter.getgCPauseDuration());
            System.out.println("old : " + parameter.getOldObjectSpaceUsed());

        }*/
        return parameterTreeMap;
    }

}
