/**
 * 
 */
package com.golf;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.golf.ioc.BeanUtils;
import com.golf.mvc.ResUtils;
import com.golf.utils.ClassUtils;
import com.golf.utils.ConfigUtils;
import com.golf.utils.JaxbUtils;
import com.golf.utils.StringUtils;

/**
 * @author Thunder.Hsu
 * 
 */
public abstract class Golf {
    public static final String charsetName = "UTF-8";
    public static final String CONFIG_FILE_NAME = "golf";/* 配置文件 */
    public static final String DATASOURCE_CONFIG_FILE = "dataSource.xml";/* 数据库配置文件 */
    public static final String DATABASE_ROUTER_FILE = "dbRouter.xml";/* 数据源路由配置文件 */
    public static final String DEFAULT_SCHEMA = "default";/* 默认数据源 */
    public static final String RUNTIME = "runtime";// 运行环境/调试环境

    private static final Logger log = LoggerFactory.getLogger(Golf.class);

    protected static String appPath = null;
    protected static String packages[] = null;

    public static String getAppPath() {
        return appPath;
    }

    public static String getProperty(String key, String def) {
        String v = ConfigUtils.get(key);
        if (StringUtils.isBlank(v)) {
            v = def;
        }
        return v;
    }

    public static void init(String... pkgs) throws Exception {
        if (null == pkgs || pkgs.length == 0) {
            pkgs = getPkgs();
            packages = pkgs;
        }
        Set<Class<?>> clzs = ClassUtils.getPackageAllClasses(pkgs);
        // jaxb
        JaxbUtils.regist(clzs);
        for (Class<?> clz : clzs) {
            // Resist mvc
            ResUtils.registerRes(clz);
            // Resist bean
            BeanUtils.registBean(clz);
        }
    }

    public static String[] getPkgs() {
        if (null != packages && packages.length != 0) {
            return packages;
        }
        String classPath = null;// classPath
        if (null != appPath) {
            classPath = appPath + "WEB-INF\\classes";
        } else {
            classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        File dir = new File(classPath);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory() && !file.getName().equals("javax") && !file.getName().equals("java")) {
                    return true;
                }
                return false;
            }
        });
        String[] pkgs = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            pkgs[i] = files[i].getName();
        }
        // 设置
        packages = pkgs;
        return pkgs;
    }

}
