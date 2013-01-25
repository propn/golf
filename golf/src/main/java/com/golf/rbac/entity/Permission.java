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
package com.golf.rbac.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.golf.dao.SqlUtils;
import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.EntityHelper;
import com.golf.dao.entity.EntitySqls;
import com.golf.dao.entity.EntityUtils;
import com.golf.dao.entity.IEntity;
import com.golf.utils.json.anno.Transient;

/**
 * 许可表（PERMISSIONS）包括许可标识、许可名称、受控对象、操作标识。许可表给出了受控对象与操作算子的对应关系。
 * 
 * @author Thunder.Hsu 2012-12-18
 */
@Table(schema = "golf", name = "PERMISSION")
public class Permission implements IEntity {

    @Transient
    private static final long serialVersionUID = -8409027515314245278L;

    @Column(columnDefinition = "BIGINT", nullable = false)
    private Long permissionId;

    @Id
    @Column(columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String permissionName;
    @Id
    @Column(columnDefinition = "VARCHAR", length = 32, nullable = false)
    private String objetctCode;

    @Column(columnDefinition = "VARCHAR", length = 32, nullable = false)
    private String operationCode;

    //
    @Transient
    private static Map<String, Long> permissionCache;

    public static Long getPermissionIdFromCache(String objetctCode, String operationCode) throws Exception {
        if (null != permissionCache) {
            return permissionCache.get(objetctCode + ":" + operationCode);
        }
        //
        List<Permission> permissions = EntityUtils.qryList(Permission.class, null);
        permissionCache = new HashMap<String, Long>();
        for (Permission permission : permissions) {
            permissionCache.put(permission.getObjetctCode() + ":" + permission.getOperationCode(),
                    permission.getPermissionId());
        }
        return permissionCache.get(objetctCode + ":" + operationCode);
    }

    //
    public static void addPermission(Permission permission, Point obj, Operation oper) throws Exception {
        permission.setObjetctCode(obj.getObjetctCode());
        permission.setOperationCode(oper.getOperationCode());
        permission.getHelper().save();
        if (null != permissionCache) {
            permissionCache.put(permission.getObjetctCode() + ":" + permission.getOperationCode(),
                    permission.getPermissionId());
        }
    }

    //
    public static void deletePermission(Permission permission) throws Exception {
        String sql = "DELETE FROM ROLE_PERMISSION_REL WHERE PERMISSION_ID=${permissionId}";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("permissionId", permission.getPermissionId());
        SqlUtils.excuteUpdate(sql, EntitySqls.getTableSchema(RolePermissionRel.class), param);
        //
        permission.getHelper().delete();
        if (null != permissionCache) {
            permissionCache.remove(permission.getObjetctCode() + ":" + permission.getOperationCode());
        }
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getObjetctCode() {
        return objetctCode;
    }

    public void setObjetctCode(String objetctCode) {
        this.objetctCode = objetctCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    /* (non-Javadoc)
     * @see com.golf.dao.entity.IEntity#getHelper()
     */
    @Override
    public EntityHelper getHelper() {
        // TODO Auto-generated method stub
        return null;
    }

}
