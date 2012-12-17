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
package com.golf.dao.po;

import org.junit.Test;

import com.golf.dao.Person;

/**
 * @author Thunder.Hsu 2012-12-17
 */
public class PoSqlsTest {

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getInsertSql(java.lang.Class)}.
     */
    @Test
    public void testGetInsertSql() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getSelectSql(java.lang.Class)}.
     * @throws Exception 
     */
    @Test
    public void testGetSelectSql() throws Exception {
        String ddl = PoSqls.getSelectSql(Person.class);
        System.out.println(ddl);
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getUpdateSql(java.lang.Class)}.
     */
    @Test
    public void testGetUpdateSql() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getDeleteSql(java.lang.Class)}.
     */
    @Test
    public void testGetDeleteSql() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getDDL(java.lang.Class)}.
     * 
     * @throws Exception
     */
    @Test
    public void testGetDDL() throws Exception {
        String ddl = PoSqls.getDDL(Person.class);
        System.out.println(ddl);
    }

    /**
     * Test method for {@link com.golf.dao.po.PoSqls#getTableSchema(java.lang.Class)}.
     */
    @Test
    public void testGetTableSchema() {
        // fail("Not yet implemented");
    }

}
