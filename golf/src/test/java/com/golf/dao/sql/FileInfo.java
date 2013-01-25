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
package com.golf.dao.sql;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.EntityHelper;
import com.golf.dao.entity.IEntity;

/**
 * @author Thunder.Hsu
 * 
 */
@Table(name = "FILE_INFO")
public class FileInfo implements IEntity {

    @Id
    @Column(name = "FILE_ID")
    long fileId;
    @Column
    String fileName;
    @Column
    String fileType;
    @Column
    String desc;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /* (non-Javadoc)
     * @see com.golf.dao.entity.IEntity#getHelper()
     */
    @Override
    public EntityHelper getHelper() {
        return new EntityHelper(this) ;
    }

}
