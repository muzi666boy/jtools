// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.spring;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The UserBean.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Configuration
@Data
public class UserBean {
    @Value("${user.userId}")
    private long userId;
    @Value("${user.userName}")
    private String userName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
