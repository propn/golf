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

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.dao.po.PoSqls;

/**
 * @author Administrator
 * 
 */
public class PoSqlsTest {

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
     * Test method for {@link com.golf.dao.po.PoSqls#getInsertSql(java.lang.Class)}.
     */
    @Test
    public void testGetInsertSql() {

    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getSelectSql(java.lang.Class)}.
     * 
     * @throws Exception
     */
    @Test
    public void testGetSelectSql() throws Exception {
        String a = PoSqls.getSelectSql(FileInfo.class);
        System.out.println(a);
        SqlParser sp = new QrySqlParser();
        FileInfo f = new FileInfo();
        Map m = f.toMap();
        a = sp.dealOptParam(a, m);
        System.out.println(a);
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getUpdateSql(java.lang.Class)}.
     */
    @Test
    public void testGetUpdateSql() {

    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getDeleteSql(java.lang.Class)}.
     */
    @Test
    public void testGetDeleteSql() {

    }

}
