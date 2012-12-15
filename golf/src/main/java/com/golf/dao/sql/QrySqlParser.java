/**
 * 
 */
package com.golf.dao.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SELECT SQL语句解析,动态SQL处理
 * 
 * @author Thunder.Hsu 2012-12-8
 */
public class QrySqlParser extends SqlParser {

    @Override
    public String dealOptParam(String sql, Map<String, Object> param) throws Exception {
        String OPTIONAL_REXP = "\\[[#=!<>${}\\w\\s]*]";
        Pattern p = Pattern.compile(OPTIONAL_REXP);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String var = m.group();
            List<String> keys = getAllVars(var);
            for (String key : keys) {
                if (!containsKey(key, param)) {
                    sql = sql.replace(var, "");
                    break;
                }
            }
            sql = sql.replace(var, var.subSequence(1, var.length() - 1));
        }
        if (p.matcher(sql).find()) {
            sql = dealOptParam(sql, param);
        }
        return sql;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String sql = "SELECT PROXYHOSTIP,NAME,PROXYPORT,TYPE FROM SYSNETPROXYCFG [WHERE NAME=${NAME} [AND TYPE=${TYPE}]] ";
        Parser parser = new QrySqlParser();
        Map parms = new HashMap();
        parms.put("NAME", "徐雷");
        // parms.put("TYPE", "IT");
        Object[] r = parser.parse(sql, parms);

        System.out.println(r[0]);
        Object[] p = (Object[]) r[1];
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + "    ");
        }

    }

}
