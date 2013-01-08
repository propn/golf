/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.mvc;

import java.util.HashMap;
import java.util.Map;

import com.golf.rbac.entity.User;
import com.golf.utils.cache.ExpiringCache;

/**
 * @author Thunder.Hsu 2012-12-18
 */
public abstract class Session {
    public static final ExpiringCache<String, Map<String, Object>> globalCache = new ExpiringCache<String, Map<String, Object>>();
    static {
        globalCache.setExpirationInterval(60);
        globalCache.setTimeToLive(1800);
    }
    
    private static ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private static ThreadLocal<Map<String, Object>> ctx = new ThreadLocal<Map<String, Object>>();

    public static void bindCtx(String sid) {
        sessionId.set(sid);
        if (null == globalCache.get(sid)) {
            globalCache.put(sid, new HashMap<String, Object>());
        }
        ctx.set(globalCache.get(sid));
    }

    public static void destroy() {
        ctx.set(new HashMap<String, Object>());
        globalCache.put(sessionId.get(), ctx.get());
    }

    public String getSessionId() {
        return sessionId.get();
    }

    public static User getUser() {
        return get(User.class.getName());
    }

    public static void bindUser(User user) {
        ctx.get().put(User.class.getName(), user);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        // return (T)globalCache.get(sessionId.get()).get(key);
        return (T) ctx.get().get(key);
    }

    public static void put(String key, Object val) {
        ctx.get().put(key, val);
        // globalCache.get(sessionId.get()).put(key, val);
    }

    public static void remove(String key) {
        ctx.get().remove(key);
        globalCache.get(sessionId.get()).remove(key);
    }
}
