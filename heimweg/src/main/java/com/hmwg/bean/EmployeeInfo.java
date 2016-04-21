package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * Created by Abaddon on 2016/3/7.
 */
public class EmployeeInfo extends Message {
    @Expose
    private Integer Id;
    @Expose
    private String EmployeeID;
    @Expose
    private String Name;
    @Expose
    private String Password;
    @Expose
    private Integer FGroup;
    @Expose
    private Integer ShopId;
    @Expose
    private String lastCodebar;
    /**
     * 是否进行过认证
     */
    @Expose
    private boolean isLogin;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getFGroup() {
        return FGroup;
    }

    public void setFGroup(Integer FGroup) {
        this.FGroup = FGroup;
    }

    public Integer getShopId() {
        return ShopId;
    }

    public void setShopId(Integer shopId) {
        ShopId = shopId;
    }

    public String getLastCodebar() {
        return lastCodebar;
    }

    public void setLastCodebar(String lastCodebar) {
        this.lastCodebar = lastCodebar;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
