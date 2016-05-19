package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * Created by eric_qiantw on 16/5/17.
 */
public class OrderInfoAPI extends Message {

    @Expose
    private int Id;
    @Expose
    private String strCreateTime;
    @Expose
    private String ShopName;
    @Expose
    private String CardNo;
    @Expose
    private String Grmxh;
    @Expose
    private String CardMan;
    @Expose
    private String PhoneNum;
    @Expose
    private String Address;
    @Expose
    private String Yjtcsj;
    @Expose
    private String StrTjsj;
    @Expose
    private String strSgsj;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getGrmxh() {
        return Grmxh;
    }

    public void setGrmxh(String grmxh) {
        Grmxh = grmxh;
    }

    public String getCardMan() {
        return CardMan;
    }

    public void setCardMan(String cardMan) {
        CardMan = cardMan;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getYjtcsj() {
        return Yjtcsj;
    }

    public void setYjtcsj(String yjtcsj) {
        Yjtcsj = yjtcsj;
    }

    public String getStrTjsj() {
        return StrTjsj;
    }

    public void setStrTjsj(String strTjsj) {
        StrTjsj = strTjsj;
    }

    public String getStrSgsj() {
        return strSgsj;
    }

    public void setStrSgsj(String strSgsj) {
        this.strSgsj = strSgsj;
    }
}
