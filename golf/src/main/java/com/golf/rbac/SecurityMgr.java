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

/**
 * @author Thunder.Hsu 2012-12-18
 */
public interface SecurityMgr<T> {

    SecurityMgr<T> getSessionMgr();

    void put(String sessionId, T obj);

    void remove(String sessionId);

    T get(String sessionId);

}
