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
import com.golf.dao.po.Entity;

/**
 * 用户指派UA (User Assignment):是用户集USERS到角色集ROLES的一种多对多的关系,<br>
 * 即UAUR。(u, r)UA为一个二元关系,表示用户u被指派了一个角色r,也就是说u拥有r所具有的权限。
 * 
 * @author Thunder.Hsu 2012-12-20
 */
@Table(schema = "golf", name = "USER_ROLE_REL")
public class UserRoleRel extends Entity {
    /**
     * 
     */
    private static final long serialVersionUID = 570644418469629032L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long userId;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
