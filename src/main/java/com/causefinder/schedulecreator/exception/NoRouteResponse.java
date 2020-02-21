package com.causefinder.schedulecreator.exception;

public class NoRouteResponse extends RuntimeException {
    public NoRouteResponse(String cause) {
        super(cause);
    }
}
