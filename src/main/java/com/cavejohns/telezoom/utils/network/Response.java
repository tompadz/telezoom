package com.cavejohns.telezoom.utils.network;

public class Response<T> {

    private final T result;
    private final Throwable error;

    private final ResponseState state;

    public Response(Throwable error, ResponseState state) {
        this.result = null;
        this.error = error;
        this.state = state;
    }

    public Response(T result, ResponseState state) {
        this.result = result;
        this.error = null;
        this.state = state;
    }

    public T getResult() {
        return result;
    }

    public Throwable getError() {
        return error;
    }

    public ResponseState getState() {
        return state;
    }
}
