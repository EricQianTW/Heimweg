package com.hmwg.bean;

import com.hmwg.base.Message;

/**
 * Created by eric_qiantw on 16/5/17.
 */
public class LocationInfo extends Message {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
