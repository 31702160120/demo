package com.example.a2233.demo;

/**
 * Created by 2233 on 2019/1/4.
 */

public class NewsInfo {
    private String name;
    private String user;

    public  NewsInfo(String name ,String user){
        this.name=name;
        this.user=user;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
