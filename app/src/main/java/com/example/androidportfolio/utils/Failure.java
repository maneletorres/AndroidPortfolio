package com.example.androidportfolio.utils;

public abstract class Failure {

    private String message;

    public Failure(String message) {
        this.message = message;
    }

    public static class GenericFailure extends Failure {

        public GenericFailure(String message) {
            super(message);
        }
    }

    public final static class InvalidCredentials extends GenericFailure {

        public InvalidCredentials(String message) {
            super(message);
        }
    }

    public final static class ConnectionFailure extends GenericFailure {

        public ConnectionFailure(String message) {
            super(message);
        }
    }
}
