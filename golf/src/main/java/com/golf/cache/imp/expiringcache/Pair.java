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
package com.golf.cache.imp.expiringcache;

/**
 * @author Thunder.Hsu 2012-12-21
 */
public class Pair<K extends Comparable, V> implements Comparable<Pair> {
    public Pair(K key1, V value1) {
        this.key = key1;
        this.value = value1;
    }

    public K key;
    public V value;

    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair p = (Pair) obj;
            return key.equals(p.key) && value.equals(p.value);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public int compareTo(Pair p) {
        int v = key.compareTo(p.key);
        if (v == 0) {
            if (p.value instanceof Comparable) {
                return ((Comparable) value).compareTo(p.value);
            }
        }
        return v;
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}
