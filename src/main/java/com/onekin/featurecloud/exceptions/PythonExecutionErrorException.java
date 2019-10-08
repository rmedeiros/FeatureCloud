package com.onekin.featurecloud.exceptions;

public class PythonExecutionErrorException extends Exception {
    public PythonExecutionErrorException() {
    }

    public PythonExecutionErrorException(String message) {
        super(message);
    }

    public PythonExecutionErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PythonExecutionErrorException(Throwable cause) {
        super(cause);
    }

    public PythonExecutionErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
