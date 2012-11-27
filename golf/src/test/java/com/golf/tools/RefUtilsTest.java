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
package com.golf.tools;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.dao.Person;

/**
 * @author Administrator
 *
 */
public class RefUtilsTest {

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
     * Test method for {@link com.golf.tools.RefUtils#getFieldValue(java.lang.Object, java.lang.String)}.
     */
    @Test
    public void testGetFieldValue() {
        Person obj = new Person();
        try {
            System.out.println(RefUtils.getFieldValue(obj, "personId"));
            System.out.println(RefUtils.getFieldValue(obj, "personName"));
            System.out.println(RefUtils.getFieldValue(obj, "age"));

            RefUtils.setFieldValue(obj, "personId", "123");
            RefUtils.setFieldValue(obj, "personName", "123");
            RefUtils.setFieldValue(obj, "age", 123);

            System.out.println(RefUtils.getFieldValue(obj, "personId"));
            System.out.println(RefUtils.getFieldValue(obj, "personName"));
            System.out.println(RefUtils.getFieldValue(obj, "age"));

        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link com.golf.tools.RefUtils#setFieldValue(java.lang.Object, java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testSetFieldValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.tools.RefUtils#getFields(java.lang.Class)}.
     */
    @Test
    public void testGetFields() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.tools.RefUtils#getMethods(java.lang.Class)}.
     */
    @Test
    public void testGetMethods() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.tools.RefUtils#getField(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetField() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.tools.RefUtils#getMethod(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetMethod() {
        fail("Not yet implemented");
    }

}
