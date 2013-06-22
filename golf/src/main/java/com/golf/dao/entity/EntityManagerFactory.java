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
package com.golf.dao.entity;

import java.util.Map;

/**
 * EntityManagerFactory用来管理EntityManager
 * 
 * @author Thunder.Hsu 2013-3-6
 */
public interface EntityManagerFactory {

    /**
     * Create a new EntityManager.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     */
    EntityManager createEntityManager();

    /**
     * Create a new EntityManager with the specified Map of
     * properties.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     */
    EntityManager createEntityManager(Map map);

    /**
     * Close the factory, releasing any resources that it holds.
     * After a factory instance is closed, all methods invoked on
     * it will throw an IllegalStateException, except for isOpen,
     * which will return false. Once an EntityManagerFactory has
     * been closed, all its entity managers are considered to be
     * in the closed state.
     */
    void close();

    /**
    * Indicates whether or not this factory is open. Returns true
    * until a call to close has been made.
    */
    public boolean isOpen();
}