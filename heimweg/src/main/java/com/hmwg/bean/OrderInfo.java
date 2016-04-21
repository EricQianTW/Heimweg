package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * Created by Abaddon on 2016/3/7.
 */
public class OrderInfo extends Message {
    @Expose
    private Integer Id;

    @Expose
    private String CreateTime;

    @Expose
    private Integer UserId;

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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
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
}
