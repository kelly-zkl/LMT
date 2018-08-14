package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * Created by qwe on 2018/6/26.
 */

public class LockEarfcnBean implements Serializable {
    private int band;
    private int earfcn;

    public LockEarfcnBean(int band, int earfcn) {
        this.band = band;
        this.earfcn = earfcn;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }
}
