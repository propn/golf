/**
 * 
 */
package com.golf.dao.po;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.dao.sql.InsertSqlParser;
import com.golf.dao.sql.SelectSqlParser;
import com.golf.dao.sql.SqlParser;
import com.golf.dao.sql.SqlRunner;
import com.golf.dao.sql.UpdateSqlParser;
import com.golf.dao.trans.ConnUtils;

/**
 * JPA工具类
 * 
 * @author Thunder.Hsu 2012-12-8
 */
public class PoUtils {

    /**
     * 
     * @param po
     * @throws Exception
     */
    public static void intsert(Po po) throws Exception {
        Class<? extends Po> clz = po.getClass();
        String sql = PoSqls.getInsertSql(clz);
        SqlParser filter = new InsertSqlParser();
        Object[] param = filter.parse(sql, po);
        String schema = PoSqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static int delete(Po po) throws Exception {
        Class<? extends Po> clz = po.getClass();
        String sql = PoSqls.getDeleteSql(clz);
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.parse(sql, po);
        String schema = PoSqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        return SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static int update(Po po) throws Exception {
        Class<? extends Po> clz = po.getClass();
        String sql = PoSqls.getUpdateSql(clz);
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.parse(sql, po);
        String schema = PoSqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        return SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static <T extends Po> List<T> qryList(Class<T> clz, Map<String, Object> param) throws Exception {
        String sql = PoSqls.getSelectSql(clz);
        SqlParser filter = new SelectSqlParser();
        Object[] stmt = filter.parse(sql, param);
        String schema = PoSqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        List<Map<String, Object>> maps = SqlRunner.qryMapList(conn, (String) stmt[0], (Object[]) stmt[1]);
        // 转换结果
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : maps) {
            T po = clz.newInstance();
            po.set(map);
            rst.add(po);
        }
        return rst;
    }

    public static <T extends Po> List<T> qryPoList(Class<T> clz, Po obj) throws Exception {
        String sql = PoSqls.getSelectSql(clz);
        SqlParser filter = new SelectSqlParser();
        Object[] param = filter.parse(sql, obj);
        String schema = PoSqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        List<Map<String, Object>> maps = SqlRunner.qryMapList(conn, (String) param[0], (Object[]) param[1]);
        // 转换结果
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : maps) {
            T po = clz.newInstance();
            po.set(map);
            rst.add(po);
        }
        return rst;
    }
}
