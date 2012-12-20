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
package com.golf.dao;

import com.golf.dao.sql.SqlRunner;
import com.golf.dao.trans.ConnUtils;
import com.golf.dao.trans.Trans;

/**
 * @author Thunder.Hsu
 * 
 */
public class SqlUtils {

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param dsCode 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteInNewTrans(final String sql, final String dsCode) throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excute(ConnUtils.getConn(dsCode), sql);
            }
        });
        return rst;
    }

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param dsCode 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteUpdateInNewTrans(final String sql, final String[] params, final String dsCode)
            throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excuteUpdate(ConnUtils.getConn(dsCode), sql, params);
            }
        });
        return rst;
    }

    /**
     * 使用嵌入事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param dsCode 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteInNestTrans(final String sql, final String dsCode) throws Exception {
        Object rst = Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excute(ConnUtils.getConn(dsCode), sql);
            }
        });
        return rst;
    }

    /**
     * 使用嵌入事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param dsCode 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteUpdateInNestTrans(final String sql, final String[] params, final String dsCode)
            throws Exception {
        Object rst = Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excuteUpdate(ConnUtils.getConn(dsCode), sql, params);
            }
        });
        return rst;
    }

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param dsCode 数据源编码
     * @return
     * @throws Exception
     */
    public static Long getSeqNextVal(final String seqName, final String dsCode) throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.qryLong(ConnUtils.getConn(dsCode), "select " + seqName + ".nextval from dual");
            }
        });
        return (Long) rst;
    }
}
