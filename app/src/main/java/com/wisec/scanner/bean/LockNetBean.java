package com.wisec.scanner.bean;

import java.io.Serializable;

/**
 * Created by qwe on 2018/6/26.
 */

public class LockNetBean implements Serializable {
    private int type;
    private String name;
    private int index;

    public LockNetBean(int type, String name, int index) {
        this.type = type;
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
