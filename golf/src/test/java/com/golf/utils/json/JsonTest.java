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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.golf.dao.Person;

/**
 * @author Thunder.Hsu 2012-12-14
 */
public class JsonTest {

    @Test
    public void testToJson() {
        List<Person> ps = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setAge(10);
        p1.setPersonId("11");
        p1.setPersonName("徐雷");
        p1.setBirthDay(new Date());
        ps.add(p1);

        Person p2 = new Person();
        p2.setAge(10);
        p2.setPersonId("11");
        p2.setPersonName("东升");
        p2.setBirthDay(new Date());
        ps.add(p2);
        String a = Json.toJson(ps);
        String b = Json.toJson(ps, "yyyy-MM-dd");
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testToObject() {
        String str = "[{\"age\":10,\"birthDay\":\"2012-12-15\",\"personId\":\"11\",\"personName\":\"徐雷\"},{\"age\":10,\"birthDay\":\"2012-12-15\",\"personId\":\"11\",\"personName\":\"东升\"}]";
        Person[] ps = Json.toObject(str, Person[].class,"yyyy-MM-dd");
        for (Person person : ps) {
            System.out.println(person.getPersonName());
            System.out.println(person.getBirthDay());
        }
    }
}
