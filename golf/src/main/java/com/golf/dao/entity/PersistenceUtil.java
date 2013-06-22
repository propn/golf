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

/**
 * @author Thunder.Hsu 2013-3-6
 */
public interface PersistenceUtil {
    
    boolean isLoaded(java.lang.Object entity);

    boolean isLoaded(java.lang.Object entity, java.lang.String attributeName);

}
