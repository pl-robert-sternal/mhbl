package com.rsternal.mhbl.dao.exceptions;

public class BaseDaoOperationException extends Exception {

    private static final long   serialVersionUID       = 4586902230823403972L;

    private static final String DEFAULF_TEXT_EXCEPTION = "An error occurred during some storage operation.";

    public BaseDaoOperationException() {
        super(DEFAULF_TEXT_EXCEPTION);
    }

    public BaseDaoOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public BaseDaoOperationException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public BaseDaoOperationException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public BaseDaoOperationException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
