package com.wengjianfeng.wanandroid.model.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wengjianfeng on 2018/4/22.
 */

public class UserBean implements Serializable {
    private static final long serialVersionUID = 6512106889445792081L;


    /**
     * collectIds : [1578]
     * email :
     * icon :
     * id : 1970
     * password : 123456
     * type : 0
     * username : wwwwjf
     */

    private String email;
    private String icon;
    private int id;
    private String password;
    private int type;
    private String username;
    private List<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
