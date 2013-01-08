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

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.Entity;

/**
 * 操作算子表（OPERATIONS）包括操作标识、操作算子名称。系统中所有受控对象的操作算子构成操作算子表。
 * 
 * @author Thunder.Hsu 2012-12-20
 */
@Table(schema = "golf", name = "OPERATIONS")
public class Operation extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = -1293185392542237638L;

    @Id
    @Column(columnDefinition = "VARCHAR", length = 32)
    private String operationCode;

    @Column(columnDefinition = "VARCHAR", length = 128)
    private String operationName;

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

}
