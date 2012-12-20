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

import com.golf.utils.Cache;

/**
 * @author Thunder.Hsu 2012-12-18
 * @param <T>
 */
public class MapSecMgr<T> implements SecurityMgr<T> {
    private static SecurityMgr mgr = null;
    private static Cache<String> cache = new Cache<String>();

    private MapSecMgr() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.session.SessionMgr#getSessionMgr()
     */
    @Override
    public SecurityMgr<T> getSessionMgr() {
        mgr = new MapSecMgr<T>();
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.session.SessionMgr#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(String sessionId, T obj) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.session.SessionMgr#remove(java.lang.String)
     */
    @Override
    public void remove(String sessionId) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.session.SessionMgr#get(java.lang.String)
     */
    @Override
    public T get(String sessionId) {
        // TODO Auto-generated method stub
        return null;
    }

}
