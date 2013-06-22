/**
 * 
 */
package com.golf.mvc.session;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Session管理器
 * 
 * @author Thunder.Hsu
 * 
 */
public class SessionManager {
    
    

}

class CacheBuild {

    /**
     * 构造Cache对象
     * 
     * @return
     */
    public static <K, V> Cache<String, Map<String, Object>> buildCache() {

        RemovalListener<String, Map<String, Object>> listener = new SessionRemovalListener();

        Cache<String, Map<String, Object>> cache = CacheBuilder.newBuilder().maximumSize(200)
                .expireAfterAccess(30, TimeUnit.MINUTES).removalListener(listener).build();

        return cache;
    }

}

/**
 * Cache超时清理
 * 
 * @author Thunder.Hsu 2013-6-22
 */
class SessionRemovalListener implements RemovalListener<String, Map<String, Object>> {
    @Override
    public void onRemoval(RemovalNotification<String, Map<String, Object>> notification) {
        if (notification.getCause() == RemovalCause.EXPIRED) {
            Map<String, Object> session = notification.getValue();

        } else {

        }
    }
}
