package com.github.lavenderx.exception;

/**
 * Created by lavenderx on 2016-04-30.
 */
public class NoDataException extends RuntimeException {

    private static final long serialVersionUID = -5386851854183707292L;

    public NoDataException() {
        super();
    }

    public NoDataException(String message) {
        super(message);
    }

    public NoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataException(Throwable cause) {
        super(cause);
    }
}
