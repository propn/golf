/**
 * 
 */
package com.golf.dao.entity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.dao.sql.InsertSqlParser;
import com.golf.dao.sql.QrySqlParser;
import com.golf.dao.sql.SqlParser;
import com.golf.dao.sql.SqlRunner;
import com.golf.dao.sql.UpdateSqlParser;
import com.golf.dao.trans.ConnUtils;
import com.golf.dao.trans.DbRouter;

/**
 * JPA工具类
 * 
 * @author Thunder.Hsu 2012-12-8
 */
public class EntityUtils {

    /**
     * 
     * @param <T>
     * @param po
     * @throws Exception
     */
    public static <T extends Entity> void buildSchema(Class<T> clz, boolean dropTable) throws Exception {
        String table = EntitySqls.getTableName(clz);
        String schema = EntitySqls.getTableSchema(clz);
        Connection conn = ConnUtils.getConn(schema);
        if (dropTable) {
            String dSql = "DROP TABLE IF EXISTS " + table;
            SqlRunner.excuteUpdate(conn, dSql, null);
        }
        String cSql = EntitySqls.getDDL(clz);
        SqlRunner.excuteUpdate(conn, cSql, null);
    }

    /**
     * 
     * @param <T>
     * @param obj
     * @throws Exception
     */
    public static <T extends Entity> void intsert(T obj) throws Exception {
        Class<? extends Entity> clz = obj.getClass();
        String sql = EntitySqls.getInsertSql(clz);
        SqlParser filter = new InsertSqlParser();
        Object[] param = filter.parse(sql, obj.toMap());
//        String schema = PoSqls.getTableSchema(clz);
        String schema = DbRouter.getSchema(obj);
        Connection conn = ConnUtils.getConn(schema);
        SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static <T extends Entity> int delete(T obj) throws Exception {
        Class<? extends Entity> clz = obj.getClass();
        String sql = EntitySqls.getDeleteSql(clz);
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.parse(sql, obj.toMap());
//        String schema = PoSqls.getTableSchema(clz);
        String schema = DbRouter.getSchema(obj);
        Connection conn = ConnUtils.getConn(schema);
        return SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    public static <T extends Entity> int update(T obj) throws Exception {
        Class<? extends Entity> clz = obj.getClass();
        String sql = EntitySqls.getUpdateSql(clz);
        SqlParser filter = new UpdateSqlParser();
        Object[] param = filter.parse(sql, obj.toMap());
//        String schema = PoSqls.getTableSchema(clz);
        String schema = DbRouter.getSchema(obj);
        Connection conn = ConnUtils.getConn(schema);
        return SqlRunner.excuteUpdate(conn, (String) param[0], (Object[]) param[1]);
    }

    /**
     * Map入参查询
     * 
     * @param clz
     * @param param
     * @return
     * @throws Exception
     */
    public static <T extends Entity> List<T> qryList(Class<T> clz, Map<String, Object> param) throws Exception {
        String sql = EntitySqls.getSelectSql(clz);
        SqlParser parser = new QrySqlParser();
        Object[] stmt = parser.parse(sql, param);
//        String schema = PoSqls.getTableSchema(clz);
        String schema = DbRouter.getSchema(clz, param);
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

    /**
     * @param <T>
     * @param po
     * @return
     * @throws Exception
     */
    public static <T extends Entity> List<T> qryAll(T obj) throws Exception {
        Class<?> clz = obj.getClass();
        String sql = EntitySqls.getSelectSql(clz);
        SqlParser filter = new QrySqlParser();
        Object[] param = filter.parse(sql, obj.toMap());
//        String schema = PoSqls.getTableSchema(clz);
        String schema = DbRouter.getSchema(obj);
        Connection conn = ConnUtils.getConn(schema);
        List<Map<String, Object>> maps = SqlRunner.qryMapList(conn, (String) param[0], (Object[]) param[1]);
        // 转换结果
        List<T> rst = new ArrayList<T>();
        for (Map<String, Object> map : maps) {
            T po = (T) clz.newInstance();
            po.set(map);
            rst.add(po);
        }
        return rst;
    }
}
