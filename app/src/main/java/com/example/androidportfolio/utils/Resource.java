package com.example.androidportfolio.utils;

public class Resource<T> {
    private Status status;
    private T data;
    private String message;

    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Resource<T> success(T data) {
        return new Resource(Status.SUCCESS, data, null);
    }

    public Resource<T> error(String msg, T data) {
        return new Resource(Status.ERROR, data, null);
    }

    public Resource<T> loading(T data) {
        return new Resource(Status.LOADING, data, null);
    }
}
