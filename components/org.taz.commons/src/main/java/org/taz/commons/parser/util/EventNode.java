package org.taz.commons.parser.util;

import java.util.Collection;

/**
 * Created by vithulan on 11/29/16.
 */
public class EventNode {
    private String eventName;
    private Collection<String> attributes;
    private String description;
    private int id;
    private String color;

    public void setEventName(String eventName) {
         this.eventName = eventName;
    }
    public void setDescription(String description) {
        this.description=description;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setAttributes(Collection<String> attributes){
        this.attributes=attributes;
    }
    public String getEventName() {
        return eventName;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public String getColor() {
        return color;
    }
    public Collection<String> getAttributes() {
        return attributes;
    }

}
