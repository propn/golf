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
package com.golf.e.h2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.golf.Golf;
import com.golf.dao.SqlUtils;

/**
 * @author Thunder.Hsu 2012-12-7
 */
public class H2Func {

    public static java.sql.Date to_date(String source, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        java.util.Date date = sdf.parse(source);
        return new java.sql.Date(date.getTime());
    }

    /**
     * This method is called when executing this sample application from the command line.
     * 
     * @param args the command line parameters
     */
    public static void main(String[] args) throws Exception {
        String sql = "CREATE ALIAS TO_DATE FOR \"com.golf.e.h2.H2Func.to_date\"";
        SqlUtils.execSqlInNewTrans(sql, null, Golf.DEFAULT_DATASOURCE_CODE);
    }

}
