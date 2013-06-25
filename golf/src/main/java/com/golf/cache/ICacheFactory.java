package com.golf.cache;

public interface ICacheFactory {

    public ICache buildCache();

    public void clearCache();

}
