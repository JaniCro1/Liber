package com.libers.unnc.liber.model.bean;

import org.litepal.crud.DataSupport;
import java.io.Serializable;
import java.sql.Date;


/**
 * Created by zengye on 2/28/17.
 */

public class User extends DataSupport implements Serializable{
    private String username;
    private String password;

    private String bowrrowTime;
    private String dueTime;
    private String isbn13;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username= username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsbn13(){return isbn13;}
    public void setIsbn13(String isbn13){this.isbn13 = isbn13;}

    public String getDueTime(){return dueTime;}
    public void setDueTime(String dueTime){ this.dueTime = dueTime;}

    public String getBowrrowTime(){return bowrrowTime;}
    public void setBowrrowTime(String bowrrowTime){ this.bowrrowTime = bowrrowTime;}


}
