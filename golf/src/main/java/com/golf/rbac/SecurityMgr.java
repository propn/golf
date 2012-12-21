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
package com.golf.rbac;

import com.golf.rbac.po.User;
import com.golf.utils.cache.ExpiringCache;

/**
 * @author Thunder.Hsu 2012-12-18
 */
public abstract class SecurityMgr {

    private static final int expirationInterval = 60;// 60s
    private static final int timeToLive = 1800;// 30ms
    private static final ExpiringCache<String, User> cache = new ExpiringCache<String, User>();

    static {
        cache.setExpirationInterval(expirationInterval);
        cache.setTimeToLive(timeToLive);
    }

    public static void put(String sessionId, User user) {
        cache.put(sessionId, user);
    }

    public static void remove(String sessionId) {
        cache.remove(sessionId);
    }

    public static User get(String sessionId) {
        return cache.get(sessionId);
    }

}
