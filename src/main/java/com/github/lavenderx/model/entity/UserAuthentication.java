package com.github.lavenderx.model.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by lavenderx on 2016-05-02.
 */
public class UserAuthentication {

    private String username;

    private String password;

    private List<String> roleList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
