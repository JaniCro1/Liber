package com.libers.unnc.liber.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by zengye on 3/4/17.
 */

public class State extends DataSupport implements Serializable{
    public String getUsername()

    {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private String username;
    private  int state;

}
