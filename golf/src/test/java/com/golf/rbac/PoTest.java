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

import org.junit.Test;

import com.golf.dao.entity.EntitySqls;
import com.golf.dao.entity.EntityUtils;
import com.golf.dao.trans.Trans;
import com.golf.rbac.entity.Group;
import com.golf.rbac.entity.Point;
import com.golf.rbac.entity.Operation;
import com.golf.rbac.entity.Permission;
import com.golf.rbac.entity.Role;
import com.golf.rbac.entity.RolePermissionRel;
import com.golf.rbac.entity.User;
import com.golf.rbac.entity.UserRoleRel;

/**
 * @author Thunder.Hsu 2012-12-20
 */
public class PoTest {

    @Test
    public void test() throws Exception {
        String groupSql = EntitySqls.getDeleteSql(RolePermissionRel.class);
        System.out.println(groupSql);
        Trans.transNest(new Trans() {
            @Override
            public Object call() throws Exception {
//                PoUtils.buildSchema(Group.class, true);
//                PoUtils.buildSchema(Objetct.class, true);
//                PoUtils.buildSchema(Operation.class, true);
//                PoUtils.buildSchema(Permission.class, true);
//                PoUtils.buildSchema(Role.class, true);
//                PoUtils.buildSchema(RolePermissionRel.class, true);
//                PoUtils.buildSchema(User.class, true);
//                PoUtils.buildSchema(UserRoleRel.class, true);
                return null;
            }

        });
    }

}
