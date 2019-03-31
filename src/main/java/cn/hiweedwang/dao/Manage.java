package cn.hiweedwang.dao;

import org.springframework.beans.factory.annotation.Autowired;

public class Manage {
    public User user = null;

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }
}
