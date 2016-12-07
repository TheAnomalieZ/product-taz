package org.taz.commons.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vithulan on 12/7/16.
 */
public class AttributeNotFoundException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(AttributeNotFoundException.class);
    public AttributeNotFoundException(String eventName, String attribute){
        logger.error(attribute+" is not found in event "+eventName);
    }
}
