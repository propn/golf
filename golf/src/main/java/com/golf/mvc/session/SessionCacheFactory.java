/**
 * 
 */
package com.golf.mvc.session;

import com.golf.mvc.session.imp.ExpiringSessionCache;
import com.golf.mvc.session.imp.GoogleSessionCache;

/**
 * @author Thunder.Hsu 2013-6-24
 */
public class SessionCacheFactory {

    public static ISessionCache<String, ISession> getGoogleSessionCache() {
        // 读取配置文件...
        return GoogleSessionCache.getInstance();
    }

    public static ISessionCache<String, ISession> getExpiringSessionCache() {
        // 读取配置文件...
        return ExpiringSessionCache.getInstance();
    }

}
