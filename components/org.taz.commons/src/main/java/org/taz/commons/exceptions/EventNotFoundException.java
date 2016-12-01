package org.taz.commons.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by vithulan on 11/29/16.
 */
public class EventNotFoundException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(EventNotFoundException.class);
    public EventNotFoundException (String eventName) {
        logger.error(eventName+" Not found in JFR");
    }
}
