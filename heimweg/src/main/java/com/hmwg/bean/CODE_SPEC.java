package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * Created by eric_qiantw on 16/5/17.
 */
public class CODE_SPEC extends Message {
    @Expose
    private int Id;
    @Expose
    private String GRMXH;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGRMXH() {
        return GRMXH;
    }

    public void setGRMXH(String GRMXH) {
        this.GRMXH = GRMXH;
    }
}
