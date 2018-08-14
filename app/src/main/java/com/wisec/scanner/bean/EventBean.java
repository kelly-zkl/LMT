package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * Created by qwe on 2018/4/11.
 */

public class EventBean implements Serializable {
    private String time;
    private int pci;
    private int earfcn;
    private int cause;

    public EventBean(int pci, int earfcn, int cause) {
        this.pci = pci;
        this.earfcn = earfcn;
        this.cause = cause;
    }

    public EventBean(String time, int pci, int earfcn, int cause) {
        this.time = time;
        this.pci = pci;
        this.earfcn = earfcn;
        this.cause = cause;
    }

    public int getCause() {
        return cause;
    }

    public void setCause(int cause) {
        this.cause = cause;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "time='" + time + '\'' +
                ", pci=" + pci +
                ", earfcn=" + earfcn +
                ", cause=" + cause +
                '}';
    }
}
