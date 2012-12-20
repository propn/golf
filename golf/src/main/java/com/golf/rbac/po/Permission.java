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
 * 许可表（PERMISSIONS）包括许可标识、许可名称、受控对象、操作标识。许可表给出了受控对象与操作算子的对应关系。
 * 
 * @author Thunder.Hsu 2012-12-18
 */
@Table(schema = "golf", name = "PERMISSIONS")
public class Permission extends Po {

    /**
     * 
     */
    private static final long serialVersionUID = -8409027515314245278L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long permissionId;

    @Column(columnDefinition = "varchar", length = 255)
    private String permissionName;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long objetctId;

    @Column(columnDefinition = "BIGINT")
    private Long operationId;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getObjetctId() {
        return objetctId;
    }

    public void setObjetctId(Long objetctId) {
        this.objetctId = objetctId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

}
