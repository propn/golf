/**
 * 
 */
package com.golf.mvc.session;

import com.golf.mvc.session.imp.ISessionCache;

/**
 * Session管理器
 * 
 * @author Thunder.Hsu
 * 
 */
public class SessionManager {

    /* 静态变量:SessionCache */
    public static ISessionCache<String, ISession> sessionCache = SessionCacheFactory.getGoogleSessionCache();
    /* 线程变量:当前Session对象 */
    static ThreadLocal<ISession> ctx = new ThreadLocal<ISession>();

    public static void clearSessionCache() {
        sessionCache.invalidateAll();
    }

    public static ISession getSession() {
        return ctx.get();
    }

    public static ISession getSession(String sessionId) {
        return sessionCache.get(sessionId);
    }

    public static ISession setSession(ISession session) {
        sessionCache.put(session.getSessionId(), session);
        ctx.set(session);
        return session;
    }

    public static void invalidateSession(String sessionId) {
        sessionCache.invalidate(sessionId);
        ctx.set(null);
    }

    public static void expSession(ISession session) {
        sessionCache.invalidate(session.getSessionId());
    }

}
