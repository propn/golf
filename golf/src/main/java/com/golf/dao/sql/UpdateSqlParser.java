/**
 * 
 */
package com.golf.dao.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * UPDATE SQL语句解析,实现入参字段更新
 * 
 * @author Thunder.Hsu 2012-12-8
 */
public class UpdateSqlParser extends SqlParser {

    @Override
    public String dealOptParam(String sql, Map<String, Object> param) throws Exception {
        List<String> vars = getVars(sql);
        for (String var : vars) {
            if (!containsKey(var, param)) {
                // ,NAME=${NAME},
                String REXP = ",?\\s*" + var + "\\s*=\\s*[$#]{0,1}(\\{" + var + "\\}|" + var + "),?";
                System.out.println(REXP);
                sql = Pattern.compile(REXP).matcher(sql).replaceAll(",");
            }
        }
        // SET ,
        sql = Pattern.compile("SET\\s*,").matcher(sql).replaceAll("SET ");
        // , WHERE
        sql = Pattern.compile(",\\s*WHERE").matcher(sql).replaceAll(" WHERE");
        return sql;
    }

    public static void main(String[] args) throws Exception {
        String sql = "UPDATE SYSNETPROXYCFG SET PROXYHOSTIP=${PROXYHOSTIP},NAME=${NAME},PROXYPORT=${PROXYPORT},TYPE=${TYPE} WHERE NAME=${name} AND TYPE=${type}";
        Parser parser = new UpdateSqlParser();

        Map parms = new HashMap();

        parms.put("PROXYPORT", "127.0.0.1");
        parms.put("NAME", "name");
        parms.put("name", "127.0.0.1");

        Object[] r = parser.parse(sql, parms);
        System.out.println(r[0]);
        Object[] p = (Object[]) r[1];
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + "    ");
        }
        System.out.println();
    }

}
