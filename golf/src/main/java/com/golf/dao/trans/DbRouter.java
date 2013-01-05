/**
 * 
 */
package com.golf.dao.trans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.golf.dao.po.Po;
import com.golf.dao.po.PoSqls;
import com.golf.utils.ConvertUtils;
import com.golf.utils.StringUtils;

/**
 * 
 * @author Thunder.Hsu 2013-1-5
 */
public class DbRouter {

    private static String defaultSchema = "";// dbRouter.xml->default
    private static final Map<String, Map<String, String>> routerInfo = new HashMap<String, Map<String, String>>();

    static {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getdefaultSchema() {
        return defaultSchema;
    }

    public static <T extends Po> String getSchema(T obj) throws Exception {
        return getSchema(obj.getClass(), obj.toMap());
    }

    public static <T extends Po> String getSchema(Class<T> clz, Map<String, Object> param) throws Exception {
        String table = PoSqls.getTableName(param.getClass());
        Map<String, String> map = routerInfo.get(table);
        if (null != map) {
            // 单库
            String schema = map.get("schema");
            if (null == map.get("exp")) {
                return schema;
            }
            // 多库
            String[] exps = map.get("exp").split("|");
            String fieldName = exps[0];
            String exp = exps[1];
            long val = ConvertUtils.convert(param.get(fieldName), long.class);
            schema = map.get(compute(exp, val));
            if (null != schema) {
                return schema;
            } else {
                return map.get("schema");
            }
        }
        String schema = PoSqls.getTableSchema(param.getClass());
        if (StringUtils.isBlank(schema)) {
            return defaultSchema;
        } else {
            return schema;
        }
    }

    public static List<String> getSchemas(String tableCode) {
        List<String> rst = new ArrayList<String>();
        for (Entry<String, String> entry : routerInfo.get(tableCode).entrySet()) {
            String val = entry.getValue();
            if (entry.getKey() != "exp" && !StringUtils.isBlank(val)) {
                rst.add(val);
            }
        }
        return rst;
    }

    /**
     * 
     * @param exp
     * @param val
     * @return
     */
    private static String compute(String exp, long val) {
        if (exp.startsWith("=")) {
            return String.valueOf(val);
        }
        if (exp.startsWith("%")) {
            long i = Long.valueOf(exp.substring(1));
            return String.valueOf(val % i);
        }
        throw new RuntimeException("数据库路由表达式配置错误!" + exp);
    }

    public static Map<String, Map<String, String>> init() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docbuilder = dbf.newDocumentBuilder();// 创建解析者
        // 初始化数据源
        InputStream is = ClassLoader.getSystemResourceAsStream("dbrouter.xml");
        if (null == is) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dbrouter.xml");
        }
        Document doc = docbuilder.parse(is);
        // 全局默认库
        Element e = (Element) doc.getElementsByTagName("default").item(0);
        defaultSchema = e.getTextContent();
        //
        NodeList nl = doc.getElementsByTagName("table");
        int len = nl.getLength();
        for (int i = 0; i < len; i++) {
            Map<String, String> map = new HashMap<String, String>();
            Element ele = (Element) nl.item(i);
            // 表名
            String table = ele.getAttribute("table");
            if (null == table) {
                throw new Exception("dbrouter.xml格式错误!表名不能为空!");
            }
            routerInfo.put(table, map);
            // 唯一数据库
            String schema = ele.getAttribute("schema");
            map.put("schema", schema);
            // 分库取模质子
            String exp = ele.getAttribute("exp");
            if (StringUtils.isBlank(exp)) {
                continue;
            }
            map.put("exp", exp);
            // 分库信息
            NodeList schemas = ele.getElementsByTagName("schema");
            for (int j = 0; j < schemas.getLength(); j++) {
                Element item = (Element) schemas.item(j);
                String temp = item.getAttribute("schema");// schema
                String valss = item.getAttribute("val");
                String[] vars = valss.split(",");
                for (String var : vars) {
                    if (null != var && !"".equals(var)) {
                        map.put(var, temp);
                    }
                }
            }
        }
        return routerInfo;
    }

    public static void main(String[] args) throws Exception {
        for (Entry<String, Map<String, String>> entry : routerInfo.entrySet()) {
            System.out.println("-------------------------------------");
            System.out.println(entry.getKey());
            Map<String, String> value = entry.getValue();
            System.out.println(value);
        }
        System.out.println("-------------------------------------");
    }

}
