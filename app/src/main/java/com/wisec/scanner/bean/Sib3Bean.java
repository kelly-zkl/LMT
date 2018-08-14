package com.wisec.scanner.bean;

/**
 * b0c0消息的sib3参数
 * Created by qwe on 2018/7/5.
 */

public class Sib3Bean {
    private int earfcn;
    private int pci;
    private int qHyst;
    private int sNonIntraSearch;
    private int threshServingLow;
    private int cellReselectionPriority;
    private int sIntraSearch;
    private int qRxLevMin;

    public Sib3Bean() {
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public int getqHyst() {
        return qHyst;
    }

    public void setqHyst(int qHyst) {
        this.qHyst = qHyst;
    }

    public int getsNonIntraSearch() {
        return sNonIntraSearch;
    }

    public void setsNonIntraSearch(int sNonIntraSearch) {
        this.sNonIntraSearch = sNonIntraSearch;
    }

    public int getThreshServingLow() {
        return threshServingLow;
    }

    public void setThreshServingLow(int threshServingLow) {
        this.threshServingLow = threshServingLow;
    }

    public int getCellReselectionPriority() {
        return cellReselectionPriority;
    }

    public void setCellReselectionPriority(int cellReselectionPriority) {
        this.cellReselectionPriority = cellReselectionPriority;
    }

    public int getsIntraSearch() {
        return sIntraSearch;
    }

    public void setsIntraSearch(int sIntraSearch) {
        this.sIntraSearch = sIntraSearch;
    }

    public int getqRxLevMin() {
        return qRxLevMin;
    }

    public void setqRxLevMin(int qRxLevMin) {
        this.qRxLevMin = qRxLevMin;
    }

    @Override
    public String toString() {
        return "Sib3Bean{" +
                "earfcn=" + earfcn +
                ", pci=" + pci +
                ", qHyst=" + qHyst +
                ", sNonIntraSearch=" + sNonIntraSearch +
                ", threshServingLow=" + threshServingLow +
                ", cellReselectionPriority=" + cellReselectionPriority +
                ", sIntraSearch=" + sIntraSearch +
                ", qRxLevMin=" + qRxLevMin +
                '}';
    }
}
