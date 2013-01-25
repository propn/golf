package com.golf.dao.trans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.golf.Golf;

/**
 * Database Connection Tools
 * 
 * @author Thunder.Hsu
 * 
 */
public class ConnUtils {

    private static final Logger log = LoggerFactory.getLogger(ConnUtils.class);
    /* 当前线程事务状态,transId序列 */
    private static final ThreadLocal<String> transStatus = new ThreadLocal<String>();
    /* {CurrentTransId,{schema,Connection}} */
    private static final ThreadLocal<Map<String, Map<String, Connection>>> connCtx = new ThreadLocal<Map<String, Map<String, Connection>>>();
    /* {transStatus,{schema,SavePoint}} */
    private static final ThreadLocal<Map<String, Map<String, Savepoint>>> savePointCtx = new ThreadLocal<Map<String, Map<String, Savepoint>>>();
    
    static String getTransStatus() {
        return transStatus.get();
    }

    static void setTransStatus(String status) {
        transStatus.set(status);
    }

    static Map<String, Map<String, Connection>> getConnCtx() {
        return connCtx.get();
    }

    static Map<String, Connection> getConnMap() {
        return getConnCtx().get(getCurrentTransId());
    }

    static String getCurrentTransId() {
        String id = transStatus.get();
        if (null == id) {
            return "";
        }
        char[] ids = id.toCharArray();
        for (int i = ids.length - 1; i >= 0;) {
            if (Integer.valueOf("" + ids[i]) == Trans.NEW) {
                return id.substring(0, i + 1);
            }
            i--;
        }
        return "";
    }

    private static int getCurrentPropagation() {
        String id = transStatus.get();
        if (null == id || id.length() == 0) {
            return 0;
        }
        return Integer.parseInt(id.substring(id.length() - 1));
    }

    /**
     * 
     * @param schema
     * @return
     * @throws Exception
     */
    public static Connection getConn(String schema) throws Exception {

        if (null == getTransStatus() || "".equals(getTransStatus())) {
            throw new Exception("当前操作不在数据库事务中,请使用Trans.transNew进行数据库操作！");
        }
        int currentPropagation = getCurrentPropagation();
        String currentTransId = getCurrentTransId();
        log.debug("get Conn in trans[{}] propagation[{}] transId[{}] ", transStatus.get(), currentPropagation,
                currentTransId);

        if (null == schema) {
            schema = DbRouter.getdefaultSchema();
        }

        Connection conn = null;
        Map<String, Map<String, Connection>> connCache = connCtx.get();// {currenttransId,{schema,Connection}}
        if (null == connCache)// 事务Cache
        {
            log.debug("init trans[{}] connCache.", getTransStatus());
            connCache = Collections.synchronizedMap(new HashMap<String, Map<String, Connection>>());
            connCtx.set(connCache);

            log.debug("init transId[{}] connMap", currentTransId);
            Map<String, Connection> connMap = Collections.synchronizedMap(new HashMap<String, Connection>());
            connCache.put(currentTransId, connMap);

            log.debug("init schema[{}] Conn ", schema);
            conn = DsUtils.getDataSource(schema).getConnection();
            if(Trans.isReadonly()){
                conn.setReadOnly(true);
            }else{
                conn.setReadOnly(false);
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            }
            connMap.put(schema, conn);
        } else {
            Map<String, Connection> connMap = connCache.get(currentTransId);
            if (null == connMap) {
                log.debug("init transId[{}] connMap", currentTransId);
                connMap = Collections.synchronizedMap(new HashMap<String, Connection>());
                connCache.put(currentTransId, connMap);

                log.debug("init schema[{}] Conn", schema);
                conn = DsUtils.getDataSource(schema).getConnection();
                //只读数据库连接
                if(Trans.isReadonly()){
                    conn.setReadOnly(true);
                }else{
                    conn.setReadOnly(false);
                    conn.setAutoCommit(false);
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                connMap.put(schema, conn);
            } else {
                conn = connMap.get(schema);
                if (null == conn) {
                    log.debug("init schema[{}] conn ", schema);
                    conn = DsUtils.getDataSource(schema).getConnection();
                    //只读数据库连接
                    if(Trans.isReadonly()){
                        conn.setReadOnly(true);
                    }else{
                        conn.setReadOnly(false);
                        conn.setAutoCommit(false);
                        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    }
                    connMap.put(schema, conn);
                }
            }
        }
        log.debug("getConn schema[{}] ", schema);
        // 嵌套事务
        if (Trans.NEST == getCurrentPropagation()) {
            Map<String, Map<String, Savepoint>> savePointCache = savePointCtx.get();// {currenttransId,{schema,savePoint}}
            if (null == savePointCache) {
                savePointCache = Collections.synchronizedMap(new HashMap<String, Map<String, Savepoint>>());
                /* {schema,SavePoint} */
                Map<String, Savepoint> savepoints = Collections.synchronizedMap(new HashMap<String, Savepoint>());
                /* 为上下文中所有Connection创建SavePoint */
                Map<String, Connection> connMap = connCache.get(currentTransId);
                Set<String> schemas = connMap.keySet();
                for (String ds : schemas) {
                    Connection c = connMap.get(ds);
                    savepoints.put(ds, c.setSavepoint());
                }
                savePointCache.put(getTransStatus(), savepoints);
                savePointCtx.set(savePointCache);
            } else {
                Map<String, Savepoint> savepoints = savePointCache.get(getTransStatus());
                if (null == savepoints) {
                    savepoints = Collections.synchronizedMap(new HashMap<String, Savepoint>());
                    savePointCache.put(getTransStatus(), savepoints);
                    Map<String, Connection> connMap = connCache.get(currentTransId);
                    Connection c = connMap.get(schema);
                    savepoints.put(schema, c.setSavepoint());
                } else {
                    Savepoint p = savepoints.get(schema);
                    if (null == p) {
                        p = conn.setSavepoint();
                        savepoints.put(schema, p);
                    }
                }
            }
        }
        return conn;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public static Connection getConn() throws Exception {
        return getConn(null);
    }

    /**
     * @throws SQLException
     * @throws Exception
     * 
     */
    static void commit() {
        String trans = getTransStatus();
        int currentPropagation = getCurrentPropagation();
        if (currentPropagation != Trans.NEW) {// 嵌入事务不提交
            log.debug("trans[{}] delegate to parent.", trans);
            return;
        }

        Map<String, Map<String, Connection>> connCache = connCtx.get();
        if (null == connCache) {
            log.debug("trans[{}] not enlist conn.", trans);
            return;
        }

        String currentTransId = getCurrentTransId();
        Map<String, Connection> connMap = connCache.get(currentTransId);
        if (null == connMap) {
            log.debug("trans[{}] not enlist conn.", trans);
            return;
        }
        for (Connection conn : connMap.values()) {
            if (conn != null) {
                try {
                    if(!Trans.isReadonly()){
                        conn.commit();
                    }
                } catch (SQLException e) {
                    log.debug("Trans[{}] commit error!：", trans, e);
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        log.debug("Trans[{}]  conn.close error!", trans, e);
                    }
                }
            }
        }
        log.debug("trans[{}] commit.", trans);
        cleanCtx();
    }

    /**
     * @throws SQLException
     * 
     */
    static void rollback() {
        // 事务传播行为
        int currentPropagation = getCurrentPropagation();
        if (currentPropagation == Trans.NEW) {
            rollbackConn();
        } else {
            Map<String, Map<String, Savepoint>> savepointCache = savePointCtx.get();
            if (null == savepointCache) {
                rollbackConn();
            } else {
                String trans = getTransStatus();
                Map<String, Savepoint> savepointMap = savepointCache.get(trans);
                if (null == savepointMap) {
                    rollbackConn();
                } else {
                    String currentTransId = getCurrentTransId();
                    Map<String, Connection> connMap = connCtx.get().get(currentTransId);
                    for (Map.Entry<String, Connection> entry : connMap.entrySet()) {
                        Connection conn = entry.getValue();
                        String schema = entry.getKey();
                        Savepoint savepoint = savepointMap.get(schema);
                        if (null == savepoint) {
                            try {// 保存点建立之后创建的数据库连接
                                if(!Trans.isReadonly()){
                                    conn.rollback();
                                }
                            } catch (SQLException e) {
                                log.debug("事务回滚失败：schema[{}] ", schema, e);
                            } finally {
                                try {
                                    conn.close();
                                    connMap.remove(schema);
                                } catch (SQLException e) {
                                    log.debug("数据库连接关闭失败：schema[{}] ", schema, e);
                                }
                            }
                        } else {
                            try {
                                if(!Trans.isReadonly()){
                                    conn.rollback(savepoint);
                                }
                            } catch (SQLException e) {
                                log.debug("事务回滚失败：schema[{}] ", schema, e);
                            }
                        }
                    }
                    // Clean SavepointMap
                    savepointMap = null;
                    savepointCache.remove(trans);
                }
            }
        }
    }

    static void rollbackConn() {
        String trans = getTransStatus();
        log.debug("trans[{}] begin rollback. ", trans);

        Map<String, Map<String, Connection>> connCache = connCtx.get();
        if (null == connCache) {
            log.debug("trans[{}] not enlist conn. ", trans);
            return;
        }

        String transId = getCurrentTransId();
        Map<String, Connection> connMap = connCache.get(transId);
        if (null == connMap) {
            log.debug("trans[{}] not enlist conn. ", trans);
            return;
        }

        for (Map.Entry<String, Connection> entry : connMap.entrySet()) {
            Connection conn = entry.getValue();
            if (conn != null) {
                try {
                    if(!Trans.isReadonly()){
                        conn.rollback();
                    }
                } catch (SQLException e) {
                    log.debug("trans[{}] rollback error! ", trans, e);
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        log.debug("trans[{}] conn close error! " + trans, e);
                    }
                }
            }
        }
        cleanCtx();
    }

    /**
     * 
     */
    private static void cleanCtx() {
        String trans = getTransStatus();
        Map<String, Map<String, Connection>> connCache = connCtx.get();
        if (null == connCache) {
            return;
        }
        connCache.remove(getCurrentTransId());
        log.debug("remove trans[{}] ", trans);
    }

    
}
