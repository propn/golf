package com.golf.mvc.session.imp;

import java.util.concurrent.TimeUnit;

import com.golf.mvc.session.ISession;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * SessionCache 存放Session
 * 
 * @author Thunder.Hsu 2013-6-24
 */
public class GoogleSessionCache implements ISessionCache<String, ISession> {

    private static Cache<String, ISession> cache = null;

    public static ISessionCache<String, ISession> getInstance() {
        RemovalListener<String, ISession> listener = new SessionRemovalListener();
        cache = CacheBuilder.newBuilder().maximumSize(200).expireAfterAccess(30, TimeUnit.MINUTES)
                .removalListener(listener).build();
        return new GoogleSessionCache();
    }

    @Override
    public ISession get(String sessionId) {
        return cache.getIfPresent(sessionId);
    }

    @Override
    public void put(String sessionId, ISession session) {
        cache.put(sessionId, session);

    }

    @Override
    public void invalidate(String sessionId) {
        cache.invalidate(sessionId);
    }

    @Override
    public long getSize() {
        return cache.size();
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }

}

/**
 * Session超时清理
 * 
 * @author Thunder.Hsu 2013-6-22
 */
class SessionRemovalListener implements RemovalListener<String, ISession> {
    @Override
    public void onRemoval(RemovalNotification<String, ISession> notification) {
        if (notification.getCause() == RemovalCause.EXPIRED) {
            ISession session = notification.getValue();
        } else {
        }
    }
}
