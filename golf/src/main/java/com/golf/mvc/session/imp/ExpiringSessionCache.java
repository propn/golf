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
package com.golf.mvc.session.imp;

import com.golf.cache.imp.ExpiringCache;
import com.golf.mvc.session.ISession;

/**
 * @author Thunder.Hsu 2012-12-18
 */
public class ExpiringSessionCache implements ISessionCache<String, ISession> {

    private static ExpiringCache<String, ISession> cache = null;

    public static ExpiringSessionCache getInstance() {
        cache.setExpirationInterval(60);
        cache.setTimeToLive(1800);
        return new ExpiringSessionCache();
    }

    @Override
    public ISession get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, ISession obj) {
        cache.put(key, obj);
    }

    @Override
    public void invalidate(String key) {
        cache.remove(key);
    }

    @Override
    public long getSize() {
        return cache.size();
    }

    @Override
    public void invalidateAll() {
        cache.clear();
    }

}
