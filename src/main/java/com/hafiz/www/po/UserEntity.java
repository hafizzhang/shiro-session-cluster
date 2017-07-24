package com.hafiz.www.po;

/**
 * Desc:用户表实体类
 * Created by hafiz.zhang on 2016/8/27.
 */
public class UserEntity {
    private Long id;            // 编号
    private String userName;    // 用户名
    private String password;    // 密码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
