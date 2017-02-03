package org.taz.core.jythonscript.jython.exception;

/**
 * Thrown when the result of an evaluated Jython script cannot be found. Any evaluated Jython script must have a local
 * variable named 'result' in order to correctly pass information back to Java.
 *
 */
public class JythonResultNotFoundException extends JythonScriptException {


    public JythonResultNotFoundException() {
        super();
    }


    public JythonResultNotFoundException(String message) {
        super(message);
    }


    public JythonResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public JythonResultNotFoundException(Throwable cause) {
        super(cause);
    }

}