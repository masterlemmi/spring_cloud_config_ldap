package com.example.demo.main.security;

public class LdapSettings {

    private String url;
    private String base;
    private String managerDn;
    private String managerPw;
    private String userBase;
    private String userFilter;
    private String groupBase;
    private String groupFilter;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getManagerDn() {
        return managerDn;
    }

    public void setManagerDn(String managerDn) {
        this.managerDn = managerDn;
    }

    public String getManagerPw() {
        return managerPw;
    }

    public void setManagerPw(String managerPw) {
        this.managerPw = managerPw;
    }

    public String getUserBase() {
        return userBase;
    }

    public void setUserBase(String userBase) {
        this.userBase = userBase;
    }

    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }

    public String getGroupBase() {
        return groupBase;
    }

    public void setGroupBase(String groupBase) {
        this.groupBase = groupBase;
    }

    public String getGroupFilter() {
        return groupFilter;
    }

    public void setGroupFilter(String groupFilter) {
        this.groupFilter = groupFilter;
    }
}
