package com.golf.dao.trans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.golf.Golf;
import com.jolbox.bonecp.BoneCPDataSource;

public class DsUtils {

    private static final Logger log = LoggerFactory.getLogger(DsUtils.class);

    private static Map<String, DataSource> cache = Collections.synchronizedMap(new HashMap<String, DataSource>());
    private static InitialContext ic = null;

    static {
        try {
            ic = new InitialContext();
            List<Map<String, String>> dss = getDsConfig();
            for (Map<String, String> ds : dss) {
                String jndi = ds.get("jndi");
                if (null == jndi || jndi.isEmpty()) {
                    cache.put(ds.get("code"), initDs(ds));
                } else {
                    cache.put(ds.get("code"), (DataSource) ic.lookup(jndi));
                }
            }
        } catch (Exception e) {
            log.error("初始化数据源错误!", e);
            throw new RuntimeException(e);
        }
    }

    private static List<Map<String, String>> getDsConfig() throws Exception {
        List<Map<String, String>> dss = new ArrayList<Map<String, String>>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docbuilder = dbf.newDocumentBuilder();// 创建解析者

        // 初始化数据源
        InputStream is = ClassLoader.getSystemResourceAsStream(Golf.DATASOURCE_FILE_NAME);
        if (null == is) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Golf.DATASOURCE_FILE_NAME);
        }
        Document doc = docbuilder.parse(is);
        NodeList nl = doc.getElementsByTagName("DataSource");
        int len = nl.getLength();
        for (int i = 0; i < len; i++) {
            Map<String, String> map = new HashMap<String, String>();
            dss.add(map);
            Element ds = (Element) nl.item(i);
            String code = getNodeValue(ds, "code");
            if (null == code) {
                throw new Exception("数据源配置文件DataSource.xml格式错误!code节点不能为空!");
            }
            map.put("code", code);

            String jndi = getNodeValue(ds, "jndi");
            if (null != jndi) {
                map.put("jndi", jndi);
                continue;
            }

            String driverClass = getNodeValue(ds, "driverClass");
            if (null == driverClass) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时driverClass节点不能为空!");
            }
            map.put("driverClass", driverClass);

            String jdbcUrl = getNodeValue(ds, "jdbcUrl");
            if (null == jdbcUrl) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时jdbcUrl节点不能为空!");
            }
            map.put("jdbcUrl", jdbcUrl);

            String username = getNodeValue(ds, "username");
            if (null == username) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时username节点不能为空!");
            }
            map.put("username", username);

            String password = getNodeValue(ds, "password");
            if (null == password) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时password节点不能为空!");
            }
            map.put("password", password);

            String maxConnectionAgeInSeconds = getNodeValue(ds, "maxConnectionAgeInSeconds");
            if (null == maxConnectionAgeInSeconds) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时maxConnectionAgeInSeconds节点不能为空!");
            }
            map.put("maxConnectionAgeInSeconds", maxConnectionAgeInSeconds);

            String partitionCount = getNodeValue(ds, "partitionCount");
            if (null == partitionCount) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时partitionCount节点不能为空!");
            }
            map.put("partitionCount", partitionCount);

            String minConnectionsPerPartition = getNodeValue(ds, "minConnectionsPerPartition");
            if (null == minConnectionsPerPartition) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时minConnectionsPerPartition节点不能为空!");
            }
            map.put("minConnectionsPerPartition", minConnectionsPerPartition);

            String maxConnectionsPerPartition = getNodeValue(ds, "maxConnectionsPerPartition");
            if (null == maxConnectionsPerPartition) {
                throw new Exception("数据源配置文件DataSource.xml配置错误!jndi为空时maxConnectionsPerPartition节点不能为空!");
            }
            map.put("maxConnectionsPerPartition", maxConnectionsPerPartition);
        }

        return dss;
    }

    private static String getNodeValue(Element el, String tagName) throws Exception {
        NodeList jndiNodeList = el.getElementsByTagName(tagName);
        if (null == jndiNodeList || jndiNodeList.getLength() < 1) {
            return null;
        }
        if (jndiNodeList.getLength() > 1) {
            throw new Exception("数据源配置文件DataSource.xml格式错误!节点[" + tagName + "]出现多次!");
        }
        Node node = jndiNodeList.item(0);
        if (null == node || null == node.getFirstChild()) {
            return null;
        }
        return node.getFirstChild().getNodeValue();
    }

    private static DataSource initDs(Map<String, String> map) throws RuntimeException {
        try {
            String driverClass = map.get("driverClass");
            String jdbcUrl = map.get("jdbcUrl");
            String username = map.get("username");
            String password = map.get("password");
            String maxConnectionAgeInSeconds = map.get("maxConnectionAgeInSeconds");
            String partitionCount = map.get("partitionCount");
            String maxConnectionsPerPartition = map.get("maxConnectionsPerPartition");
            String minConnectionsPerPartition = map.get("minConnectionsPerPartition");

            Class.forName(driverClass);
            // 连接
            BoneCPDataSource dataSource = new BoneCPDataSource();
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            // 超时
            dataSource.setMaxConnectionAgeInSeconds(Long.valueOf(maxConnectionAgeInSeconds));
            // 连接数
            dataSource.setPartitionCount(Integer.parseInt(partitionCount));
            dataSource.setMinConnectionsPerPartition(Integer.parseInt(minConnectionsPerPartition));
            dataSource.setMaxConnectionsPerPartition(Integer.parseInt(maxConnectionsPerPartition));
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("初始化数据源失败!", e);
        }
    }

    static DataSource getDataSource(String code) throws Exception {
        DataSource dataSource = cache.get(code);
        return dataSource;
    }
}
