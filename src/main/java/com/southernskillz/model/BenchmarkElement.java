package com.southernskillz.model;

import java.io.Serializable;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class BenchmarkElement implements Serializable{

    private static final long serialVersionUID = 42L;
    private String benchId;
    private String benchMessage;

    public String getBenchId() {
        return benchId;
    }

    public void setBenchId(String benchId) {
        this.benchId = benchId;
    }

    public String getBenchMessage() {
        return benchMessage;
    }

    public void setBenchMessage(String benchMessage) {
        this.benchMessage = benchMessage;
    }
}
