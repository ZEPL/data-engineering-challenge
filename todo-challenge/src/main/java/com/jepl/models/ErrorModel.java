package com.jepl.models;

public class ErrorModel {
    private String message = "";

    public ErrorModel() {
        this("");
    }

    public ErrorModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorModel{" +
                "message='" + message + '\'' +
                '}';
    }
}
