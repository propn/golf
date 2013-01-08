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
package com.golf.mvc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Thunder.Hsu 2013-1-8
 */
public class SessionTest {

    @Test
    public void testBindCtx() {
        String sid = "2012";
        Session.bindCtx(sid);

        Map map = new HashMap();
        map.put("2012", 2012);
        Session.put("abc", map);

        Map map2 = Session.get("abc");
        Object obj = map2.get("2012");
        System.out.println(obj);
        map2.put("2012", 2013);

        Map map3 = Session.get("abc");
        Object obj2 = map3.get("2012");
        System.out.println(obj2);
        
        System.out.println(Session.globalCache.get("2012"));

    }

}
