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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Thunder.Hsu 2012-12-17
 */
public class SqlParsertTest {

    /**
     * Test method for {@link com.golf.dao.sql.SqlParser#checkWhereCondition(java.lang.String)}.
     */
    @Test
    public void testCheckWhereCondition() {
        String WHERE_REXP = "\\[[\\S\\s]WHERE[\\S\\s]\\]";
        String sql = "select * from person [ WHERE ]";
        Pattern p = Pattern.compile(WHERE_REXP);
        Matcher m = p.matcher(sql);
        sql = sql.replaceAll(WHERE_REXP, "");
        System.out.println(sql);
        while (m.find()) {
            String var = m.group();
            System.out.println(var);
        }

    }

}
