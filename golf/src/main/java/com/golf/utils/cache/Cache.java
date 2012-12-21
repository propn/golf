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
package com.golf.utils.cache;

/**
 * @author Thunder.Hsu 2012-12-21
 */
public interface Cache<K extends Comparable, V> {

    V get(K obj);

    void put(K key, V obj);

    void put(K key, V obj, long validTime);

    void remove(K key);

    Pair[] getAll();

    int size();
}