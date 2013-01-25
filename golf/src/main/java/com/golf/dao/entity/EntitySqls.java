package com.golf.dao.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.Golf;
import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.trans.DbRouter;
import com.golf.utils.RefUtils;
import com.golf.utils.StringUtils;
import com.golf.utils.cache.PartitionCache;

/**
 * 
 * @author Thunder.Hsu 2012-12-14
 */
public class EntitySqls {
    //
    private static PartitionCache<String> sqlCache = new PartitionCache<String>();
    private static PartitionCache<String> fieldColumnMap = new PartitionCache<String>();
    private static PartitionCache<String> columnFieldMap = new PartitionCache<String>();

    /**
     * 通过字段名找属性名
     * 
     * @param clz
     * @param columnName
     * @return
     * @throws Exception
     */
    public static <T extends IEntity> String getFieldName(Class<T> clz, String columnName) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return columnFieldMap.get(className, columnName);
    }

    /**
     * 通过属性名找字段名
     * 
     * @param clz
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static <T extends IEntity> String getColumnName(Class<T> clz, String fieldName) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return fieldColumnMap.get(className, fieldName);
    }

    private static <T extends IEntity> void build(Class<T> clz) throws Exception {
        String className = clz.getName();
        buildFields(clz);
        sqlCache.put(className, "C", generalInsertSql(clz));
        sqlCache.put(className, "R", generalSelectSql(clz));
        sqlCache.put(className, "U", generalUpdateSql(clz));
        sqlCache.put(className, "D", generalDeleteSql(clz));
    }

    public static <T extends IEntity> String getInsertSql(Class<T> clz) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return sqlCache.get(className, "C");
    }

    public static <T extends IEntity> String getSelectSql(Class<T> clz) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return sqlCache.get(className, "R");
    }

    public static <T extends IEntity> String getUpdateSql(Class<T> clz) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return sqlCache.get(className, "U");
    }

    public static <T extends IEntity> String getDeleteSql(Class<T> clz) throws Exception {
        String className = clz.getName();
        if (sqlCache.get(className).isEmpty()) {
            build(clz);
        }
        return sqlCache.get(className, "D");
    }

    public static <T extends IEntity> String getDDL(Class<T> clz) throws Exception {
        return generalDDL(clz);
    }

    /**
     * 构造建表语句
     * 
     * @param <T>
     * 
     * @param clz
     * @return
     * @throws Exception
     */
    private static <T extends IEntity> String generalDDL(Class<T> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("CREATE TABLE " + tableName + " (");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            String primarykey = null;// 主键
            for (Field field : columnFields) {
                Column column = field.getAnnotation(Column.class);
                // 列名
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName);
                // 类型
                String columnType = column.columnDefinition().toUpperCase();
                if (StringUtils.isBlank(columnType)) {
                    throw new Exception("属性" + columnName + " @Column未设置columnDefinition属性");
                }
                sqlStr.append(" ");
                sqlStr.append(columnType);
                // 长度
                int length = column.length();
                if (length != 255) {
                    sqlStr.append("(").append(length).append(")");
                } else if (columnType.contains("CHAR")) {
                    sqlStr.append("(").append(length).append(")");
                }
                // NULL
                boolean nullable = column.nullable();
                if (!nullable) {
                    sqlStr.append(" NOT NULL");
                } else {
                    Id id = field.getAnnotation(Id.class);
                    if (null != id) {
                        sqlStr.append(" NOT NULL");
                    }
                }
                sqlStr.append(",");
            }
        }
        // PRIMARY KEY
        List<Field> idFields = getIdFields(clz);
        if (!idFields.isEmpty()) {
            sqlStr.append("PRIMARY KEY (");
            for (Field field : idFields) {
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName);
                sqlStr.append(",");
            }
            sqlStr = sqlStr.replace(sqlStr.lastIndexOf(","), sqlStr.length(), "))");
        } else {
            sqlStr = sqlStr.replace(sqlStr.lastIndexOf(","), sqlStr.length(), ")");
        }
        return sqlStr.toString();
    }

    private static <T extends IEntity> void buildFields(Class<T> clz) throws Exception {
        String className = clz.getName();
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String column = getColumnName(clz, field.getName());
                columnFieldMap.put(className, column, field.getName());
                fieldColumnMap.put(className, field.getName(), column);
            }
        }
    }

    private static <T extends IEntity> String generalInsertSql(Class<T> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("INSERT INTO " + tableName + " (");
        StringBuffer valueStr = new StringBuffer(" VALUES (");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String column = getColumnName(clz, field.getName());
                sqlStr.append(column).append(",");
                valueStr.append("${").append(field.getName()).append("}").append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), ")");
        valueStr.replace(valueStr.length() - 1, valueStr.length(), ")");
        return sqlStr.append(valueStr).toString();
    }

    private static <T extends IEntity> String generalDeleteSql(Class<T> clz) throws Exception {
        StringBuffer sqlStr = new StringBuffer("DELETE FROM ");
        sqlStr.append(getTableName(clz));
        sqlStr.append(" WHERE ");
        // WHERE
        List<Field> ids = getIdFields(clz);
        if (!ids.isEmpty()) {
            for (Field field : ids) {
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName).append("=${").append(field.getName()).append("}").append(" AND ");
            }
            sqlStr.replace(sqlStr.length() - 4, sqlStr.length(), "");
        } else {
            throw new Exception(clz.getName() + " 没有主键");
        }
        return sqlStr.toString();
    }

    private static <T extends IEntity> String generalUpdateSql(Class<T> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("UPDATE " + tableName + " SET ");

        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName).append("=").append("${").append(field.getName()).append("}").append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), "");
        List<Field> ids = getIdFields(clz);
        if (!ids.isEmpty()) {
            sqlStr.append(" WHERE ");
            for (Field field : ids) {
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName).append("=${").append(field.getName()).append("}").append(" AND ");
            }
            sqlStr.replace(sqlStr.length() - 4, sqlStr.length(), "");
        } else {
            throw new Exception(clz.getName() + " 没有主键");
        }
        return sqlStr.toString();
    }

    private static <T extends IEntity> String generalSelectSql(Class<T> clz) throws Exception {
        StringBuffer sqlStr = new StringBuffer("SELECT ");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append(columnName).append(" ").append(field.getName()).append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), " FROM ");
        sqlStr.append(getTableName(clz));
        // WHERE
        // List<Field> ids = getIdFields(clz); 所有字段
        if (!columnFields.isEmpty()) {
            sqlStr.append(" [ WHERE ");
            for (int i = 0; i < columnFields.size(); i++) {
                Field field = columnFields.get(i);
                String columnName = getColumnName(clz, field.getName());
                sqlStr.append("[");
                if (i > 0) {
                    sqlStr.append("AND ");
                }
                sqlStr.append(columnName).append("=${").append(field.getName()).append("}").append("]");
            }
            sqlStr.append("]");
        }
        return sqlStr.toString();
    }

    public static <T extends IEntity> String getTableSchema(Class<T> clz) throws Exception {
        String className = clz.getName();
        if (null == sqlCache.get(className).get("S")) {
            String schema = null;
            if (clz.isAnnotationPresent(Table.class)) {
                schema = ((Table) clz.getAnnotation(Table.class)).schema().toUpperCase();
            }
            if ("".equals(schema)) {
                schema = Golf.DEFAULT_SCHEMA;
            }
            sqlCache.put(className, "S", schema);
        }
        return sqlCache.get(className).get("S");
    }

    public static <T extends IEntity> String getTableName(Class<T> clz) throws Exception {
        String table = null;
        if (clz.isAnnotationPresent(Table.class)) {
            table = ((Table) clz.getAnnotation(Table.class)).name().toUpperCase();
        }
        if (null == table) {
            throw new Exception(clz.getName() + " 没有设定表名");
        }
        return table;
    }

    private static <T extends IEntity> List<Field> getIdFields(Class<T> clz) {
        Map<String, Field> map = RefUtils.getFields(clz);
        Object[] fields = map.values().toArray();

        List<Field> list = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++) {
            Field field = (Field) fields[i];
            if (field.isAnnotationPresent(Column.class) && field.isAnnotationPresent(Id.class)) {
                list.add(field);
            }
        }
        return list;
    }

    private static <T extends IEntity> List<Field> getColumnFields(Class<T> clz) {
        List<Field> list = new ArrayList<Field>();
        Map<String, Field> map = RefUtils.getFields(clz);
        Object[] fields = map.values().toArray();
        for (int i = 0; i < fields.length; i++) {
            Field field = (Field) fields[i];
            if (field.isAnnotationPresent(Column.class)) {
                list.add(field);
            }
        }
        return list;
    }

}
