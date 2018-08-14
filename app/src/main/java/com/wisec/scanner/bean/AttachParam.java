package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * attach自动测试参数
 * Created by qwe on 2018/6/19.
 */

public class AttachParam implements Serializable {
    private int time;
    private int space;
    private int count;
    private boolean isOpen;

    public AttachParam(int time, int space, int count, boolean isOpen) {
        this.time = time;
        this.space = space;
        this.count = count;
        this.isOpen = isOpen;
    }

    public AttachParam(int time, int space, int count) {
        this.time = time;
        this.space = space;
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
