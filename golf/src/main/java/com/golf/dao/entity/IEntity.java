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

import com.golf.dao.anno.Entity;

/**
 * @author Thunder.Hsu 2013-1-25
 */
@Entity
public interface IEntity {
    EntityHelper getHelper();
}
