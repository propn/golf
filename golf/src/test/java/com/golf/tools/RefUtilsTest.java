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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.dao.Person;
import com.golf.utils.RefUtils;

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
     * Test method for {@link com.golf.utils.RefUtils#getFieldValue(java.lang.Object, java.lang.String)}.
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
     * Test method for {@link com.golf.utils.RefUtils#setFieldValue(java.lang.Object, java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testSetFieldValue() {
    }

    /**
     * Test method for {@link com.golf.utils.RefUtils#getFields(java.lang.Class)}.
     */
    @Test
    public void testGetFields() {
    }

    /**
     * Test method for {@link com.golf.utils.RefUtils#getMethods(java.lang.Class)}.
     */
    @Test
    public void testGetMethods() {
    }

    /**
     * Test method for {@link com.golf.utils.RefUtils#getField(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetField() {
    }

    /**
     * Test method for {@link com.golf.utils.RefUtils#getMethod(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetMethod() {
    }

}
