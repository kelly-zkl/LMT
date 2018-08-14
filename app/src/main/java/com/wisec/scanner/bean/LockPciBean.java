package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * Created by qwe on 2018/6/26.
 */

public class LockPciBean implements Serializable {
    private int band;
    private int pci;
    private int earfcn;

    public LockPciBean(int band, int pci, int earfcn) {
        this.band = band;
        this.pci = pci;
        this.earfcn = earfcn;
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
}
