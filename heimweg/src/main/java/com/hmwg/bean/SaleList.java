package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * Created by eric_qiantw on 16/5/17.
 */
public class SaleList extends Message {

    @Expose
    private int Id;
    @Expose
    private String ShopName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
