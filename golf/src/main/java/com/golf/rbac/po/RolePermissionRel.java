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
 * 权限指派PA (Permission Assignment):是权限集P到角色集ROLES的一种多对多的关系,<br>
 * 即PAPR。(p , r)PA为一个二元关系,表示权限p被赋予角色r,也就是说拥有r的用户拥有p
 * 
 * @author Thunder.Hsu 2012-12-20
 */
@Table(schema = "golf", name = "ROLE_PERMISSION_REL")
public class RolePermissionRel extends Po{
    /**
     * 
     */
    private static final long serialVersionUID = 4036459568598078984L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long roleId;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

}
