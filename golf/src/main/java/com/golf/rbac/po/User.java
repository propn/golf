/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.rbac.po;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.po.Po;

/**
 * 用户表（USERS）包括用户标识、用户姓名、用户登录密码。用户表是系统中的个体用户集，随用户的添加与删除动态变化。
 * 
 * @author Thunder.Hsu 2012-12-18
 */
@Table(schema = "golf", name = "USERS")
public class User extends Po {

    /**
     * 
     */
    private static final long serialVersionUID = -98300522688849208L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long userId;

    @Column(columnDefinition = "varchar", length = 64)
    private String userName;

    @Column(columnDefinition = "BIGINT")
    private Long groupId;

    @Column(columnDefinition = "varchar", length = 128)
    private String email;

    @Column(columnDefinition = "TINYINT")
    private Byte status;

    // 登录名
    @Column(columnDefinition = "varchar", length = 16)
    private String userCode;

    // AES加密
    @Column(columnDefinition = "varchar", length = 64)
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
