package com.wisec.scanner.bean;

/**
 * Created by qwe on 2018/4/11.
 */

public class VillageBean {
    public static int LOCAL_CILLAGE = 1;
    public static int NEAR_CILLAGE = 2;
    private int type;
    private int band;
    private int pci;
    private int earfcn;
    private double rsrp;
    private double rsrq;
    private long stamp;

    public VillageBean(int type, int band, int pci, int earfcn, double rsrp, double rsrq, long stamp) {
        this.type = type;
        this.band = band;
        this.pci = pci;
        this.earfcn = earfcn;
        this.rsrp = rsrp;
        this.rsrq = rsrq;
        this.stamp = stamp;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
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

    public double getRsrp() {
        return rsrp;
    }

    public void setRsrp(double rsrp) {
        this.rsrp = rsrp;
    }

    public double getRsrq() {
        return rsrq;
    }

    public void setRsrq(double rsrq) {
        this.rsrq = rsrq;
    }
}
