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
import com.golf.dao.sql.SqlMapExe;
import com.golf.dao.sql.SqlParser;
import com.golf.dao.sql.UpdateSqlParser;
import com.golf.dao.trans.ConnUtils;

/**
 * @author Thunder.Hsu
 * 
 */
public class PoUtils {

    public static void intsert(Po po) throws Exception {
        String sql = PoSqls.getInsertSql(po.getClass());
        SqlParser filter = new InsertSqlParser();
        Object[] param = filter.doFilter(sql, po);
        Connection conn = ConnUtils.getConn();
        SqlMapExe.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static <T extends Po> List<T> qryList(Class<T> clazz, Map<String, Object> param) throws Exception {
        String sql = PoSqls.getSelectSql(clazz);
        SqlParser filter = new SelectSqlParser();
        Object[] stmt = filter.doFilter(sql, param);
        Connection conn = ConnUtils.getConn();
        List<Map<String, Object>> maps = SqlMapExe.qryMapList(conn, (String) stmt[0], (Object[]) stmt[1]);
        // 转换结果
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : maps) {
            T po = clazz.newInstance();
            po.set(map);
            rst.add(po);
        }
        return rst;
    }

    public static <T> List<T> qryPoList(Po obj) throws Exception {
        String sql = PoSqls.getSelectSql(obj.getClass());
        SqlParser filter = new SelectSqlParser();
        Object[] param = filter.doFilter(sql, obj);
        Connection conn = ConnUtils.getConn();
        List<Map<String, Object>> maps = SqlMapExe.qryMapList(conn, (String) param[0], (Object[]) param[1]);
        // 转换结果
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : maps) {
            Po po = obj.getClass().newInstance();
            po.set(map);
            rst.add((T) po);
        }
        return rst;
    }

    public static int update(Po po) throws Exception {
        String sql = PoSqls.getUpdateSql(po.getClass());
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.doFilter(sql, po);
        Connection conn = ConnUtils.getConn();
        return SqlMapExe.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static int delete(Po po) throws Exception {
        String sql = PoSqls.getDeleteSql(po.getClass());
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.doFilter(sql, po);
        Connection conn = ConnUtils.getConn();
        return SqlMapExe.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
