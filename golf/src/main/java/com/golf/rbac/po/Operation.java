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
 * 操作算子表（OPERATIONS）包括操作标识、操作算子名称。系统中所有受控对象的操作算子构成操作算子表。
 * 
 * @author Thunder.Hsu 2012-12-20
 */
@Table(schema = "golf", name = "OPERATIONS")
public class Operation extends Po {

    /**
     * 
     */
    private static final long serialVersionUID = -1293185392542237638L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long operationId;

    @Column(columnDefinition = "VARCHAR", length = 128)
    private String operationName;

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

}