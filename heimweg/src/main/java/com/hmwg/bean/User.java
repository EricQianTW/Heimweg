package com.hmwg.bean;

import com.google.gson.annotations.Expose;
import com.hmwg.base.Message;

/**
 * 用户信息
 *
 * @author wqf
 *
 */
@SuppressWarnings("rawtypes")
public class User extends Message {
    /**
     * 用户名称
     */
    @Expose
    private Integer userId;
    /**
     * 用户名称
     */
    @Expose
    private String userName;
    /**
     * 用户账号
     */
    @Expose
    private String account;
    /**
     * 密码
     */
    @Expose
    private String password;
    /**
     * 手机号
     */
    @Expose
    private String mobile;
    /**
     * 是否进行过认证
     */
    @Expose
    private boolean isLogin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
