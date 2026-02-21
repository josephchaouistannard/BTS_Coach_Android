package com.example.coach.api;

public class ResponseApi<T> {
    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    private int code;
    private String message;
    private T result;
}
