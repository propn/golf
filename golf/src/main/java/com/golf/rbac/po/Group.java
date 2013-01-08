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
 * 　Group：用户组，权限分配的单位与载体。权限不考虑分配给特定的用户而给组。 组可以包括组(以实现权限的继承)，也可以包含用户，组内用户继承组的权限。<br>
 * User与Group是多对多的关系。 Group可以层次化， 以满足不同层级权限控制的要求
 * 
 * @author Thunder.Hsu 2012-12-19
 */
@Table(schema = "golf", name = "GROUPS")
public class Group extends Entity {
    private static final long serialVersionUID = -7401027991552704209L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long groupId;

    @Column(columnDefinition = "varchar", length = 255)
    private String groupName;

    @Column(columnDefinition = "BIGINT")
    private Long parentGroupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Long parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
