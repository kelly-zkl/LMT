package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * Created by qwe on 2018/6/26.
 */

public class LockBandBean implements Serializable {
    private boolean band1;
    private boolean band3;
    private boolean band5;
    private boolean band7;
    private boolean band8;
    private boolean band38;
    private boolean band39;
    private boolean band40;
    private boolean band41;

    public LockBandBean(boolean band1, boolean band3, boolean band5, boolean band7, boolean band8, boolean band38, boolean band39, boolean band40, boolean band41) {
        this.band1 = band1;
        this.band3 = band3;
        this.band5 = band5;
        this.band7 = band7;
        this.band8 = band8;
        this.band38 = band38;
        this.band39 = band39;
        this.band40 = band40;
        this.band41 = band41;
    }

    public boolean isBand1() {
        return band1;
    }

    public void setBand1(boolean band1) {
        this.band1 = band1;
    }

    public boolean isBand3() {
        return band3;
    }

    public void setBand3(boolean band3) {
        this.band3 = band3;
    }

    public boolean isBand5() {
        return band5;
    }

    public void setBand5(boolean band5) {
        this.band5 = band5;
    }

    public boolean isBand7() {
        return band7;
    }

    public void setBand7(boolean band7) {
        this.band7 = band7;
    }

    public boolean isBand8() {
        return band8;
    }

    public void setBand8(boolean band8) {
        this.band8 = band8;
    }

    public boolean isBand38() {
        return band38;
    }

    public void setBand38(boolean band38) {
        this.band38 = band38;
    }

    public boolean isBand39() {
        return band39;
    }

    public void setBand39(boolean band39) {
        this.band39 = band39;
    }

    public boolean isBand40() {
        return band40;
    }

    public void setBand40(boolean band40) {
        this.band40 = band40;
    }

    public boolean isBand41() {
        return band41;
    }

    public void setBand41(boolean band41) {
        this.band41 = band41;
    }
}
