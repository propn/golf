/**
 * 
 */
package com.golf.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.dao.sql.InsertSqlParser;
import com.golf.dao.sql.Po;
import com.golf.dao.sql.SelectSqlParser;
import com.golf.dao.sql.SqlFilter;
import com.golf.dao.sql.SqlMapExe;
import com.golf.dao.sql.SqlUtils;
import com.golf.dao.sql.UpdateSqlParser;
import com.golf.dao.trans.ConnUtils;

/**
 * @author Thunder.Hsu
 * 
 */
public class PoUtils {

    public static void intsert(Po po) throws Exception {
        String sql = SqlUtils.getInsertSql(po.getClass());
        SqlFilter filter = new InsertSqlParser();
        Object[] param = filter.doFilter(sql, po);
        Connection conn = ConnUtils.getConn();
        SqlMapExe.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static <T> List<T> qryPoList(Po obj) throws Exception {
        String sql = SqlUtils.getSelectSql(obj.getClass());
        SqlFilter filter = new SelectSqlParser();
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
        String sql = SqlUtils.getUpdateSql(po.getClass());
        SqlFilter filter = new UpdateSqlParser();
        Object[] param = filter.doFilter(sql, po);
        Connection conn = ConnUtils.getConn();
        return SqlMapExe.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static int delete(Po po) throws Exception {
        String sql = SqlUtils.getDeleteSql(po.getClass());
        SqlFilter filter = new UpdateSqlParser();
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
