package com.jepl.enums;

import java.io.*;

public enum TaskStatus implements Serializable {
    NOT_DONE("NOT_DONE"), DONE("DONE");

    TaskStatus(String status) {
        this.status = status;
    }
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
