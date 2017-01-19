package org.taz.core.clustering.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vithulan on 1/16/17.
 */
public class Parameter {
    private String eventName;
    private List<String> attributes = null;

    public Parameter(String eventName){
        this.eventName = eventName;
    }

    public void addAttribute(String attribute){
        if(attributes==null){
            attributes = new ArrayList<String>();
            attributes.add(attribute);
        }
        else
            attributes.add(attribute);
    }

    public List<String> getAttributes(){
        return attributes;
    }

    public String getEventName(){
        return eventName;
    }
}
