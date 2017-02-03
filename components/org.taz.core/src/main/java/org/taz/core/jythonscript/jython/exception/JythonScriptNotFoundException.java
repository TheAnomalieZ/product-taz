package org.taz.core.jythonscript.jython.exception;

import java.io.File;
import java.io.InputStream;

/**
 * Thrown when a given Jython script (represented as a {@link String}, {@link File}, or {@link InputStream}) cannot be
 * located on the host system.
 *
 */
public class JythonScriptNotFoundException extends JythonScriptException {


    public JythonScriptNotFoundException() {
        super();
    }


    public JythonScriptNotFoundException(String message) {
        super(message);
    }


    public JythonScriptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public JythonScriptNotFoundException(Throwable cause) {
        super(cause);
    }

}