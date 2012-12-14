/**
 * 
 */
package com.golf.dao.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Thunder.Hsu
 * 
 */
public class InsertSqlParser extends SqlParser {

    @Override
    public String dealOptParam(String sql, Map<String, Object> param) throws Exception {
        String REXP = "";
        List<String> vars = getVars(sql);
        for (String var : vars) {
            if (!containsKey(var, param)) {
                // ,${V},|,${V}|#{V}
//                REXP = ",?//s*[$#]{0,1}(\\{" + var + "\\}|" + var + ")//s*,?";
                REXP = ",?//s*[$#]{0,1}(\\{" + var + "\\}|" + var + ")//s*,?";
                System.out.println(REXP);
                sql = Pattern.compile(REXP).matcher(sql).replaceAll(",");
                System.out.println(sql);
            }
        }
        REXP = ",//s*[)]";// ,)
        sql = Pattern.compile(REXP).matcher(sql).replaceAll(")");
        REXP = "[(]//s*,";// (,
        sql = Pattern.compile(REXP).matcher(sql).replaceAll("(");
        return sql;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String sql = "INSERT INTO SYSNETPROXYCFG (PROXYHOSTIP,NAME,PROXYPORT,TYPE) VALUES (${PROXYHOSTIP},${NAME},${PROXYPORT},${TYPE})";

        Parser sqlParser = new InsertSqlParser();
        Map parms = new HashMap();
        parms.put("PROXYPORT", "127.0.0.1");
        Object[] r = sqlParser.parse(sql, parms);
        System.out.println(r[0]);
        Object[] p = (Object[]) r[1];
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + "    ");
        }
        System.out.println();
    }
}
