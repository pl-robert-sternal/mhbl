package com.rsternal.mhbl.main.service.exceptions;

public class BaseServiceOperationException extends Exception {

    private static final long   serialVersionUID                 = 1L;

    private static final String DEFAULT_OPERATION_EXCEPTION_TEXT = "An error occured during some service operation.";

    public BaseServiceOperationException() {
        super(DEFAULT_OPERATION_EXCEPTION_TEXT);
    }

    public BaseServiceOperationException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public BaseServiceOperationException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public BaseServiceOperationException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public BaseServiceOperationException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
