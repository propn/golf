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
package com.golf.utils.json;

import org.junit.Test;

import com.golf.dao.Person;

/**
 * @author Thunder.Hsu 2012-12-14
 */
public class JsonTest {

    @Test
    public void testToJson() {
        Person p = new Person();
        p.setAge(10);
        p.setPersonId("11");
        p.setPersonName("徐雷");
        String a = Json.toJson(p);
        System.out.println(a);
    }

    @Test
    public void testToObject() {
        String str = "{\"age\":10,\"personId\":\"11\",\"personName\":\"徐雷\"}";
        Person p = Json.toObject(str, Person.class);
        System.out.println(p.getPersonName());
    }
}
