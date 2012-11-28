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
package com.golf.dao.trans;

import org.junit.Test;

/**
 * @author Administrator
 * 
 */
public class DsUtilsTest {

    /**
     * Test method for {@link com.golf.dao.trans.DsUtils#getDataSource(java.lang.String)}.
     * 
     * @throws Exception
     */
    @Test
    public void testGetDataSource() throws Exception {
        DsUtils.getDataSource(null);
    }

}
