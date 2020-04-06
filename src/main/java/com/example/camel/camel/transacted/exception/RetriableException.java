package com.example.camel.camel.transacted.exception;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
public class RetriableException extends RuntimeException {

    public RetriableException() {
    }

    public RetriableException(String message) {
        super(message);
    }

    public RetriableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetriableException(Throwable cause) {
        super(cause);
    }

    public RetriableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
