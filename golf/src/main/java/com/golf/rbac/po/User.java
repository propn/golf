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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.golf.dao.SqlUtils;
import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.po.Po;
import com.golf.dao.po.PoSqls;
import com.golf.utils.json.anno.Transient;

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

    @Transient
    private List<Role> roles;
    @Transient
    private List<Long> permissionIds;

    /**
     * 
     * @param userCode
     * @param password
     * @return
     * @throws Exception
     */
    public static User login(String userCode, String password) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userCode", userCode);
        param.put("password", password);
        param.put("status", 0);
        //
        List<User> users = SqlUtils.queryList(User.class, PoSqls.getSelectSql(User.class),
                PoSqls.getTableSchema(User.class), param);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public boolean hasPermission(String objetctCode, String operationCode) throws Exception {
        long permissionId = Permission.getPermissionIdFromCache(objetctCode, operationCode);
        if (null != permissionIds) {
            return permissionIds.contains(permissionId);
        }
        //
        String sql = "SELECT PERMISSION_ID FROM ROLE_PERMISSION_REL WHERE ROLE_ID IN ("
                + "SELECT ROLE_ID FROM USER_ROLE_REL WHERE USER_ID=${userId})";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", this.userId);
        permissionIds = SqlUtils.querySingleObjectList(Long.class, sql, PoSqls.getTableSchema(Role.class), param);
        return permissionIds.contains(permissionId);
    }

    // ------
    public List<Role> getRoles() throws Exception {
        if (null != roles) {
            return roles;
        }
        //
        String sql = "SELECT STATUS status,ROLE_NAME roleName,ROLE_ID roleId FROM ROLES WHERE ROLE_ID IN ("
                + "SELECT ROLE_ID FROM USER_ROLE_REL WHERE USER_ID=${userId})";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", this.userId);
        roles = SqlUtils.queryList(Role.class, sql, PoSqls.getTableSchema(Role.class), param);
        return roles;
    }

    //
    public void addRole(Role role) throws Exception {
        UserRoleRel urr = new UserRoleRel();
        urr.setUserId(userId);
        urr.setRoleId(role.getRoleId());
        urr.save();
    }

    //
    public void deleteRole(Role role) throws Exception {
        UserRoleRel urr = new UserRoleRel();
        urr.setUserId(userId);
        urr.setRoleId(role.getRoleId());
        urr.delete();
    }

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
