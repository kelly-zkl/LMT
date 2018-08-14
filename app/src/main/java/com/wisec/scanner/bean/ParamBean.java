package com.wisec.scanner.bean;

/**
 * 重选参数实体类
 * Created by qwe on 2018/5/11.
 */

public class ParamBean {
    private int c_earfcn;//服务小区频点
    private int c_pci;
    private int c_cellRe;//服务小区的优先级
    private int earfcn;//频点
    private String ploy;//重选策略
    private String high;//高优先级
    private String low;//低优先级
    private String equal;//同等优先级
    private int lev;//优先级
    private int qRxLev389;
    private int threH389;
    private int qOffset389;
    private int threL389;
    private boolean isShow;

    public ParamBean(int c_earfcn, int c_pci, int c_cellRe, int earfcn, String ploy, String high, String low,
                     String equal, int lev, int qRxLev389, int threH389, int qOffset389, int threL389, boolean isShow) {
        this.c_earfcn = c_earfcn;
        this.c_pci = c_pci;
        this.c_cellRe = c_cellRe;
        this.earfcn = earfcn;
        this.ploy = ploy;
        this.high = high;
        this.low = low;
        this.equal = equal;
        this.lev = lev;
        this.qRxLev389 = qRxLev389;
        this.threH389 = threH389;
        this.qOffset389 = qOffset389;
        this.threL389 = threL389;
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getC_pci() {
        return c_pci;
    }

    public void setC_pci(int c_pci) {
        this.c_pci = c_pci;
    }

    public int getqRxLev389() {
        return qRxLev389;
    }

    public void setqRxLev389(int qRxLev389) {
        this.qRxLev389 = qRxLev389;
    }

    public int getThreH389() {
        return threH389;
    }

    public void setThreH389(int threH389) {
        this.threH389 = threH389;
    }

    public int getqOffset389() {
        return qOffset389;
    }

    public void setqOffset389(int qOffset389) {
        this.qOffset389 = qOffset389;
    }

    public int getThreL389() {
        return threL389;
    }

    public void setThreL389(int threL389) {
        this.threL389 = threL389;
    }

    public int getC_cellRe() {
        return c_cellRe;
    }

    public void setC_cellRe(int c_cellRe) {
        this.c_cellRe = c_cellRe;
    }

    public int getC_earfcn() {
        return c_earfcn;
    }

    public void setC_earfcn(int c_earfcn) {
        this.c_earfcn = c_earfcn;
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }

    public String getPloy() {
        return ploy;
    }

    public void setPloy(String ploy) {
        this.ploy = ploy;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getEqual() {
        return equal;
    }

    public void setEqual(String equal) {
        this.equal = equal;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    @Override
    public String toString() {
        return "ParamBean{" +
                "c_earfcn=" + c_earfcn +
                ", c_pci=" + c_pci +
                ", c_cellRe=" + c_cellRe +
                ", earfcn=" + earfcn +
                ", ploy='" + ploy + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", equal='" + equal + '\'' +
                ", lev=" + lev +
                ", qRxLev389=" + qRxLev389 +
                ", threH389=" + threH389 +
                ", qOffset389=" + qOffset389 +
                ", threL389=" + threL389 +
                ", isShow=" + isShow +
                '}';
    }
}
