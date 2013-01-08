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
package com.golf.rbac.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.golf.dao.SqlUtils;
import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.Entity;
import com.golf.dao.entity.EntitySqls;
import com.golf.utils.json.anno.Transient;

/**
 * Role：角色，一定数量的权限的集合。权限分配的单位与载体,目的是隔离User与Privilege的逻辑关系.<br>
 * 角色表（ROLES）包括角色标识、角色名称、角色基数、角色可用标识。角色表是系统角色集，由系统管理员定义角色。
 * 
 * @author Thunder.Hsu 2012-12-18
 */
@Table(schema = "golf", name = "ROLE")
public class Role extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1513131784146134749L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long roleId;

    @Column(columnDefinition = "VARCHAR", length = 64)
    private String roleName;

    @Column(columnDefinition = "TINYINT")
    private Byte status;

    //
    @Transient
    private List<Permission> permissions;

    public List<Permission> getPermission() throws Exception {
        if (null != permissions) {
            return permissions;
        }
        String sql = "SELECT PERMISSION_NAME permissionName,OBJETCT_CODE objetctCode,PERMISSION_ID permissionId,OPERATION_CODE operationCode FROM PERMISSIONS WHERE PERMISSION_ID IN(SELECT PERMISSION_ID permissionId,ROLE_ID roleId FROM ROLE_PERMISSION_REL WHERE ROLE_ID=${roleId})";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roleId", this.roleId);
        permissions = SqlUtils.queryList(Permission.class, sql, EntitySqls.getTableSchema(Permission.class), param);
        return permissions;
    }

    public void addPermission(Permission permission) throws Exception {
        RolePermissionRel rpr = new RolePermissionRel();
        rpr.setRoleId(roleId);
        rpr.setPermissionId(permission.getPermissionId());
        rpr.save();
        if (null != permissions) {
            permissions.add(permission);
        }
    }

    public void deletePermission(Permission permission) throws Exception {
        RolePermissionRel rpr = new RolePermissionRel();
        rpr.setRoleId(roleId);
        rpr.setPermissionId(permission.getPermissionId());
        rpr.delete();
        if (null != permissions) {
            getPermission();
        }
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
