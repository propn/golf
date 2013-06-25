package com.golf.cache;

public interface ICache<K, V> {

    V get(K key);

    void put(K key, V obj);

    void invalidate(K key);

    long getSize();
    
    void invalidateAll();

}
