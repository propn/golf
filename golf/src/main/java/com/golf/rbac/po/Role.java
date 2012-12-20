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
 * Role：角色，一定数量的权限的集合。权限分配的单位与载体,目的是隔离User与Privilege的逻辑关系.<br>
 * 角色表（ROLES）包括角色标识、角色名称、角色基数、角色可用标识。角色表是系统角色集，由系统管理员定义角色。
 * 
 * @author Thunder.Hsu 2012-12-18
 */
@Table(schema = "golf", name = "ROLES")
public class Role extends Po {

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
