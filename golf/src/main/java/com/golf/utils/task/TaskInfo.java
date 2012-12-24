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
package com.golf.utils.task;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;

/**
 * @author Thunder.Hsu 2012-12-24
 */
@Table(schema = "golf", name = "TASK_INFO")
public class TaskInfo {

    @Id
    @Column(columnDefinition = "BIGINT")
    private long taskId;

    @Column(columnDefinition = "varchar", length = 256)
    private String desc;

    @Column(columnDefinition = "varchar", length = 128)
    private String clz;

    @Column(columnDefinition = "INT")
    private long period; // 间隔时间(秒)

    @Column(columnDefinition = "TINYINT")
    private Byte status;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
