package com.wisec.scanner.bean;

/**
 * Created by qwe on 2018/6/20.
 */

public class SignalBean {
    private String type;
    private String name;
    private String time;
    private String content;
    private boolean isUp;

    public SignalBean() {
    }

    public SignalBean(String type, String name, String time, String content, boolean isUp) {
        this.type = type;
        this.name = name;
        this.time = time;
        this.content = content;
        this.isUp = isUp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }
}
