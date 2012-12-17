package com.golf.dao.po;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golf.Golf;
import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.utils.Cache;
import com.golf.utils.RefUtils;
import com.golf.utils.StringUtils;

/**
 * 
 * @author Thunder.Hsu 2012-12-14
 */
public class PoSqls {

    private static Cache<String> cache = new Cache<String>();

    public static String getInsertSql(Class<?> clz) throws Exception {
        String className = clz.getName();
        if (cache.get(className).isEmpty()) {
            cache.put(className, "C", generalInsertSql(clz));
            cache.put(className, "R", generalSelectSql(clz));
            cache.put(className, "U", generalUpdateSql(clz));
            cache.put(className, "D", generalDeleteSql(clz));
        }
        return cache.get(className, "C").toString();
    }

    public static String getSelectSql(Class<?> clz) throws Exception {
        String className = clz.getName();
        if (cache.get(className).isEmpty()) {
            cache.put(className, "C", generalInsertSql(clz));
            cache.put(className, "R", generalSelectSql(clz));
            cache.put(className, "U", generalUpdateSql(clz));
            cache.put(className, "D", generalDeleteSql(clz));
        }
        return cache.get(className, "R").toString();
    }

    public static String getUpdateSql(Class<?> clz) throws Exception {
        String className = clz.getName();
        if (cache.get(className).isEmpty()) {
            cache.put(className, "C", generalInsertSql(clz));
            cache.put(className, "R", generalSelectSql(clz));
            cache.put(className, "U", generalUpdateSql(clz));
            cache.put(className, "D", generalDeleteSql(clz));
        }
        return cache.get(className, "U").toString();
    }

    public static String getDeleteSql(Class<?> clz) throws Exception {
        String className = clz.getName();
        if (cache.get(className).isEmpty()) {
            cache.put(className, "C", generalInsertSql(clz));
            cache.put(className, "R", generalSelectSql(clz));
            cache.put(className, "U", generalUpdateSql(clz));
            cache.put(className, "D", generalDeleteSql(clz));
        }
        return cache.get(className, "D").toString();
    }

    public static String getDDL(Class<?> clz) throws Exception {
        return generalDDL(clz);
    }

    /**
     * 构造建表语句
     * 
     * @param clz
     * @return
     * @throws Exception
     */
    private static String generalDDL(Class<?> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("CREATE TABLE " + tableName + " (");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                Column column = field.getAnnotation(Column.class);
                // 列名
                String columnName = column.name().toUpperCase();
                if (StringUtils.isBlank(columnName)) {
                    columnName = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(columnName);
                // 类型
                String columnType = column.columnDefinition().toUpperCase();
                if (StringUtils.isBlank(columnName)) {
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
                // nullable
                boolean nullable = column.nullable();
                if (!nullable) {
                    sqlStr.append(" NOT NULL");
                }
                sqlStr.append(",");
            }
        }
        sqlStr = sqlStr.replace(sqlStr.lastIndexOf(","), sqlStr.length(), ")");
        return sqlStr.toString();
    }

    private static String generalInsertSql(Class<?> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("INSERT INTO " + tableName + " (");
        StringBuffer valueStr = new StringBuffer(" VALUES (");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append(",");
                valueStr.append("${").append(field.getName()).append("}").append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), ")");
        valueStr.replace(valueStr.length() - 1, valueStr.length(), ")");
        return sqlStr.append(valueStr).toString();
    }

    private static String generalDeleteSql(Class<?> clz) throws Exception {
        StringBuffer sqlStr = new StringBuffer("DELETE FROM ");
        sqlStr.append(getTableName(clz));
        sqlStr.append(" WHERE ");
        // Where
        List<Field> ids = getIdFields(clz);
        if (!ids.isEmpty()) {
            for (Field field : ids) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append("=${").append(field.getName()).append("}").append(" AND ");
            }
            sqlStr.replace(sqlStr.length() - 4, sqlStr.length(), "");
        } else {
            throw new Exception(clz.getName() + " 没有主键");
        }
        return sqlStr.toString();
    }

    private static String generalUpdateSql(Class<?> clz) throws Exception {
        String tableName = getTableName(clz);
        StringBuffer sqlStr = new StringBuffer("UPDATE " + tableName + " SET ");

        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append("=").append("${").append(field.getName()).append("}").append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), "");
        List<Field> ids = getIdFields(clz);
        if (!ids.isEmpty()) {
            sqlStr.append(" WHERE ");
            for (Field field : ids) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append("=${").append(field.getName()).append("}").append(" AND ");
            }
            sqlStr.replace(sqlStr.length() - 4, sqlStr.length(), "");
        } else {
            throw new Exception(clz.getName() + " 没有主键");
        }
        return sqlStr.toString();
    }

    private static String generalSelectSql(Class<?> clz) throws Exception {
        StringBuffer sqlStr = new StringBuffer("SELECT ");
        List<Field> columnFields = getColumnFields(clz);
        if (columnFields != null && columnFields.size() > 0) {
            for (Field field : columnFields) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append(" ").append(field.getName()).append(",");
            }
        }
        sqlStr.replace(sqlStr.length() - 1, sqlStr.length(), " FROM ");
        sqlStr.append(getTableName(clz));
        // Where
        List<Field> ids = getIdFields(clz);
        if (!ids.isEmpty()) {
            sqlStr.append(" [ WHERE ");
            for (Field field : ids) {
                String column = field.getAnnotation(Column.class).name().toUpperCase();
                if (StringUtils.isBlank(column)) {
                    column = StringUtils.camel4underline(field.getName());
                }
                sqlStr.append(column).append("=${").append(field.getName()).append("}").append(" AND ");
            }
            sqlStr.replace(sqlStr.length() - 4, sqlStr.length(), "]");
        }
        return sqlStr.toString();
    }

    public static String getTableSchema(Class<?> clz) throws Exception {
        String className = clz.getName();
        if (null == cache.get(className).get("S")) {
            String schema = null;
            if (clz.isAnnotationPresent(Table.class)) {
                schema = ((Table) clz.getAnnotation(Table.class)).schema().toUpperCase();
            }
            if (null == schema) {
                schema = Golf.DEFAULT_DATASOURCE_CODE;
            }
            cache.put(className, "S", schema);
        }
        return cache.get(className).get("S");
    }

    private static String getTableName(Class<?> clz) throws Exception {
        String table = null;
        if (clz.isAnnotationPresent(Table.class)) {
            table = ((Table) clz.getAnnotation(Table.class)).name().toUpperCase();
        }
        if (null == table) {
            throw new Exception(clz.getName() + " 没有设定表名");
        }
        return table;
    }

    private static List<Field> getIdFields(Class<?> clz) {
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

    private static List<Field> getColumnFields(Class<?> clz) {
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
