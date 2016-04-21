package com.hmwg.base;

import java.io.Serializable;

public class Message<T> implements Serializable {
    private String Token;
    private String CustomCode;
    private String CustomMessage;
    private String StackTrace;
    private int State;
    private T Body;

    public T getBody() {
        return Body;
    }

    public void setBody(T Body) {
        this.Body = Body;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getCustomCode() {
        return CustomCode;
    }

    public void setCustomCode(String customCode) {
        CustomCode = customCode;
    }

    public String getCustomMessage() {
        return CustomMessage;
    }

    public void setCustomMessage(String customMessage) {
        CustomMessage = customMessage;
    }

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}