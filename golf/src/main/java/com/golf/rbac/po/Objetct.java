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
 * 客体表（OBJECTS）包括对象标识、对象名称。客体表是系统中所有受控对象的集合。
 * 
 * @author Thunder.Hsu 2012-12-20
 */
@Table(schema = "golf", name = "OBJECTS")
public class Objetct extends Po {
    /**
     * 
     */
    private static final long serialVersionUID = -6661042781607359127L;

    @Id
    @Column(columnDefinition = "BIGINT")
    private Long objetctId;

    @Column(columnDefinition = "VARCHAR", length = 128)
    private String objetctName;

    public Long getObjetctId() {
        return objetctId;
    }

    public void setObjetctId(Long objetctId) {
        this.objetctId = objetctId;
    }

    public String getObjetctName() {
        return objetctName;
    }

    public void setObjetctName(String objetctName) {
        this.objetctName = objetctName;
    }
}
