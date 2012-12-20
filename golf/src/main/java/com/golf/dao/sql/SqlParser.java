/**
 * 
 */
package com.golf.dao.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Thunder.Hsu
 * 
 */
public abstract class SqlParser implements Parser {

    // 查找变量的正则表达式，如${var1}这样的格式
    public static final String VARIABLE_REXP = "\\u0024\\u007B\\S+?}";
    // 查找SQL中需要直接替换的地方，如#{var1}，与${var1}的区别为，${var1}替换成问号，#{var1}直接替换成具体的字符
    public static final String REPLACE_REXP = "#\\u007B\\S+?}";
    // 使用中括号“[]”括起来的为可选SQL语句，当可选语句里面的#{var1}或者${var1}为空时，则认为是不需要此查询条件，直接去掉
    public static final String OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";
    // 找出所有变量，包括#{var1}、${var1}
    public static final String FIND_ALL_QUERY_VARIABLE_REXP = "\\u007B\\S+?}";
    // 找出没有入参的WHERE条件[ WHERE ]
    public static final String WHERE_REXP = "\\[[\\S\\s]WHERE[\\S\\s]\\]";

    public static List<SqlParser> getSqlParse(String sql) {
        List<SqlParser> sqlParsers = new ArrayList<SqlParser>();
        if (sql.contains("SELECT")) {
            sqlParsers.add(new QrySqlParser());
        }
        if (sql.contains("INSERT")) {
            sqlParsers.add(new InsertSqlParser());
        }
        if (sql.contains("UPDATE")) {
            sqlParsers.add(new UpdateSqlParser());
        }
        return sqlParsers;
    }

    @Override
    public Object[] parse(String sql, final Map<String, Object> param) throws Exception {
        // 处理[]
        sql = dealOptParam(sql, param);
        // 处理 #{}
        sql = replaceParam(sql, param);
        // 处理[ WHERE ]
        sql = checkWhereCondition(sql);
        // 处理 ${}
        return compileSql(sql, param);
    }

    String dealOptParam(String sql, Map<String, Object> param) throws Exception {
        return null;
    }

    public static String checkWhereCondition(String sql) {
        return sql.replaceAll(WHERE_REXP, sql);
    }

    /**
     * 将SQL语句中#{var1}替换成对应变量，直接替换，不使用？预编译字符
     * 
     * @param sql
     * @param param
     * @throws Exception
     */
    public static String replaceParam(String sql, Map<String, Object> param) throws Exception {
        // 用正则解析出变量
        Pattern p = Pattern.compile(REPLACE_REXP);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String var = m.group();
            // 去掉首尾的多余字符串
            var = var.substring(2, var.length() - 1);
            String v = String.valueOf(getParam(var, param));
            sql = sql.replaceAll(m.group(), v);
        }
        return sql;
    }

    public static Object[] compileSql(String sql, Map<?, ?> params) throws Exception {
        List<String> varList = getVars(sql);
        List<Object> param = new ArrayList<Object>();
        for (String var : varList) {
            param.add(getParam(var, params));
        }
        String REXP = "\\$?\\{(\\S*?)\\}";
        sql = Pattern.compile(REXP).matcher(sql).replaceAll("?");
        return new Object[] { sql, param.toArray() };
    }

    private static Object getParam(String var, Map<?, ?> param) {
        String[] vars = var.split("//.");
        if (vars.length == 1) {
            return param.get(vars[0]);
        } else if (vars.length > 1 && param.get(vars[0]) instanceof Map) {
            Map<?, ?> p = (Map<?, ?>) param.get(vars[0]);
            return getParam(var.replaceFirst(vars[0] + ".", ""), p);
        }
        return null;
    }

    public static boolean containsKey(String var, Map<?, ?> param) {
        String[] vars = var.split("\\.");
        if (vars.length == 1) {
            return param.containsKey(var);
        } else if (vars.length > 1 && param.get(vars[0]) instanceof Map) {
            Map<?, ?> p = (Map<?, ?>) param.get(vars[0]);
            return containsKey(var.replaceFirst(vars[0] + ".", ""), p);
        }
        return false;
    }

    /**
     * 根据查询获取入参变量${}
     */
    public static List<String> getVars(String sql) throws Exception {
        List<String> vars = new ArrayList<String>();
        Pattern p = Pattern.compile(VARIABLE_REXP);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String var = m.group();
            vars.add(var.substring(2, var.length() - 1));
        }
        return vars;
    }

    /**
     * 获取入参变量${}和#{}
     */
    public static List<String> getAllVars(String sql) throws Exception {
        List<String> vars = new ArrayList<String>();
        String FIND_ALL_QUERY_VARIABLE_REXP = "[$#]\\{\\S+\\}";
        Pattern p = Pattern.compile(FIND_ALL_QUERY_VARIABLE_REXP);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String var = m.group();
            vars.add(var.substring(2, var.length() - 1));
        }
        return vars;
    }

}
