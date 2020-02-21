package com.causefinder.schedulecreator.exception;

public class BigQuerySearchException extends RuntimeException {

    public BigQuerySearchException(final String message) {
        super(message);
    }

    public BigQuerySearchException(final String message, final Exception exception) {
        super(message, exception);
    }
}
