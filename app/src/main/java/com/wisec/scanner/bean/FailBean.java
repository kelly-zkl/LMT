package com.wisec.scanner.bean;

/**
 * Created by qwe on 2018/4/24.
 */

public class FailBean {
    private String time;
    private String result;

    public FailBean(String time, String result) {
        this.time = time;
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
