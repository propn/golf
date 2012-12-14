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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.dao.trans.ConnUtils;
import com.golf.dao.trans.Trans;

/**
 * @author Administrator
 * 
 */
public class SqlMapExeTest {

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
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#qryMap(java.sql.Connection, java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testQryMap() {
    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#qryMapList(java.sql.Connection, java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testQryMapList() {
    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlRunner#qryString(java.sql.Connection, java.lang.String)}.
     */
    @Test
    public void testQryString() {

    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#qryOne(java.sql.Connection, java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testQryOne() {

    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#excuteUpdate(java.sql.Connection, java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testExcuteUpdate() {

    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#excuteBatchUpdate(java.sql.Connection, java.lang.String, java.util.List)}.
     */
    @Test
    public void testExcuteBatchUpdate() {

    }

    /**
     * Test method for {@link com.golf.dao.sql.SqlRunner#getSeqNextVal(java.sql.Connection, java.lang.String)}.
     * 
     * @throws Exception
     * @throws SQLException
     */
    @Test
    public void testGetSeqNextVal() throws SQLException, Exception {

        Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
//                String sql = "call next value for SEQ_FILE_INFO_FILE_ID";
                String sql = "SELECT SEQ_FILE_INFO_FILE_ID.nextval FROM dual; ";
                long a = SqlRunner.qryLong(ConnUtils.getConn(),sql);
                System.out.println(a);
                return null;
            }
        });

    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#callDbFunc(java.sql.Connection, java.lang.String, java.lang.String[])}.
     */
    @Test
    public void testCallDbFunc() {

    }

    /**
     * Test method for
     * {@link com.golf.dao.sql.SqlRunner#callProc(java.sql.Connection, java.lang.String, java.util.List, int[])}.
     */
    @Test
    public void testCallProc() {

    }

}
