package com.github.lavenderx.exception;

/**
 * Created by lavenderx on 2016-04-28.
 */
public class PageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3959684858997208114L;

    public PageNotFoundException() {
        super();
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageNotFoundException(Throwable cause) {
        super(cause);
    }

}
