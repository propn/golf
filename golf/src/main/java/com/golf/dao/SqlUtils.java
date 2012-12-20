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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.dao.po.Po;
import com.golf.dao.sql.QrySqlParser;
import com.golf.dao.sql.SqlParser;
import com.golf.dao.sql.SqlRunner;
import com.golf.dao.sql.UpdateSqlParser;
import com.golf.dao.trans.ConnUtils;
import com.golf.dao.trans.Trans;
import com.golf.utils.ConvertUtils;

/**
 * @author Thunder.Hsu
 * 
 */
public class SqlUtils {

    public static int excuteUpdate(final String sql, final String schema, Map<String, Object> param) throws Exception {
        SqlParser parser = new UpdateSqlParser();
        Object[] stmt = parser.parse(sql, param);
        return SqlRunner.excuteUpdate(ConnUtils.getConn(schema), (String) stmt[0], (Object[]) stmt[1]);
    }

    public static <T> List<T> querySingleObjectList(Class<T> clz, final String sql, final String schema,
            Map<String, Object> param) throws Exception {
        SqlParser parser = new QrySqlParser();
        Object[] stmt = parser.parse(sql, param);
        List<Object> rsts = SqlRunner.qrySingleObjectList(ConnUtils.getConn(schema), (String) stmt[0],
                (Object[]) stmt[1]);
        List<T> rst = new ArrayList<T>();
        for (Object obj : rsts) {
            rst.add(ConvertUtils.convert(obj, clz));
        }
        return rst;
    }

    public static <T extends Po> List<T> queryList(Class<T> clz, final String sql, final String schema,
            Map<String, Object> param) throws Exception {
        SqlParser parser = new QrySqlParser();
        Object[] stmt = parser.parse(sql, param);
        List<Map<String, Object>> rstm = SqlRunner.qryMapList(ConnUtils.getConn(schema), (String) stmt[0],
                (Object[]) stmt[1]);
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : rstm) {
            T po = clz.newInstance();
            po.set(map);
            rst.add(po);
        }
        return rst;
    }

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param schema 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteInNewTrans(final String sql, final String schema) throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excute(ConnUtils.getConn(schema), sql);
            }
        });
        return rst;
    }

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param schema 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteUpdateInNewTrans(final String sql, final String[] params, final String schema)
            throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excuteUpdate(ConnUtils.getConn(schema), sql, params);
            }
        });
        return rst;
    }

    /**
     * 使用嵌入事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param schema 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteInNestTrans(final String sql, final String schema) throws Exception {
        Object rst = Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excute(ConnUtils.getConn(schema), sql);
            }
        });
        return rst;
    }

    /**
     * 使用嵌入事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param schema 数据源编码
     * @return
     * @throws Exception
     */
    public static Object excuteUpdateInNestTrans(final String sql, final String[] params, final String schema)
            throws Exception {
        Object rst = Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.excuteUpdate(ConnUtils.getConn(schema), sql, params);
            }
        });
        return rst;
    }

    /**
     * 使用新事务执行Sql
     * 
     * @param sql Sql语句
     * @param params Sql参数
     * @param schema 数据源编码
     * @return
     * @throws Exception
     */
    public static Long getSeqNextVal(final String seqName, final String schema) throws Exception {
        Object rst = Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return SqlRunner.qrySingleLong(ConnUtils.getConn(schema), "select " + seqName + ".nextval from dual");
            }
        });
        return (Long) rst;
    }
}
