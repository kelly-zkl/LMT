package com.wisec.scanner.bean;

/**
 * xml重选参数的bean
 * Created by qwe on 2018/5/12.
 */

public class ParamXmlBean {
    private int dlCarrierFreq;
    private int cellReselectionPriority;
    private int qRxLevMin;
    private int threshXHigh;
    private int threshXLow;
    private int qOffsetFreq;

    public ParamXmlBean() {
    }

    public ParamXmlBean(int dlCarrierFreq, int cellReselectionPriority, int qRxLevMin, int threshXHigh, int threshXLow, int qOffsetFreq) {
        this.dlCarrierFreq = dlCarrierFreq;
        this.cellReselectionPriority = cellReselectionPriority;
        this.qRxLevMin = qRxLevMin;
        this.threshXHigh = threshXHigh;
        this.threshXLow = threshXLow;
        this.qOffsetFreq = qOffsetFreq;
    }

    public int getDlCarrierFreq() {
        return dlCarrierFreq;
    }

    public void setDlCarrierFreq(int dlCarrierFreq) {
        this.dlCarrierFreq = dlCarrierFreq;
    }

    public int getCellReselectionPriority() {
        return cellReselectionPriority;
    }

    public void setCellReselectionPriority(int cellReselectionPriority) {
        this.cellReselectionPriority = cellReselectionPriority;
    }

    public int getqRxLevMin() {
        return qRxLevMin;
    }

    public void setqRxLevMin(int qRxLevMin) {
        this.qRxLevMin = qRxLevMin;
    }

    public int getThreshXHigh() {
        return threshXHigh;
    }

    public void setThreshXHigh(int threshXHigh) {
        this.threshXHigh = threshXHigh;
    }

    public int getThreshXLow() {
        return threshXLow;
    }

    public void setThreshXLow(int threshXLow) {
        this.threshXLow = threshXLow;
    }

    public int getqOffsetFreq() {
        return qOffsetFreq;
    }

    public void setqOffsetFreq(int qOffsetFreq) {
        this.qOffsetFreq = qOffsetFreq;
    }

    @Override
    public String toString() {
        return "ParamXmlBean{" +
                "dlCarrierFreq=" + dlCarrierFreq +
                ", cellReselectionPriority=" + cellReselectionPriority +
                ", qRxLevMin=" + qRxLevMin +
                ", threshXHigh=" + threshXHigh +
                ", threshXLow=" + threshXLow +
                ", qOffsetFreq=" + qOffsetFreq +
                '}';
    }
}
