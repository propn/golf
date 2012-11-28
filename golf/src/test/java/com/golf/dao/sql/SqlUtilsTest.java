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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.dao.Student;

/**
 * @author Thunder.Hsu
 *
 */
public class SqlUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlUtils#getInsertSql(java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testGetInsertSql() throws Exception {
        System.out.println(SqlUtils.getInsertSql(Student.class));
    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlUtils#getSelectSql(java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testGetSelectSql() throws Exception {
        System.out.println(SqlUtils.getSelectSql(Student.class));
    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlUtils#getUpdateSql(java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testGetUpdateSql() throws Exception {
        System.out.println(SqlUtils.getUpdateSql(Student.class));
    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlUtils#getDeleteSql(java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testGetDeleteSql() throws Exception {
        System.out.println(SqlUtils.getDeleteSql(Student.class));
    }
    
    

}
