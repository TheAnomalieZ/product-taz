package org.taz.core.jythonscript.jython.exception;

/**
 * Generic Jython script execution/evaluation exception thrown in potentially unknown circumstances during Jython script
 * execution or evaluation.
 *
 */
public class JythonScriptException extends Exception {


    public JythonScriptException() {
        super();
    }

    public JythonScriptException(String message) {
        super(message);
    }

    public JythonScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public JythonScriptException(Throwable cause) {
        super(cause);
    }

}