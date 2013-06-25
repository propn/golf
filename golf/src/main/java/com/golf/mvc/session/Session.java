/**
 * 
 */
package com.golf.mvc.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.golf.rbac.entity.User;

/**
 * 
 * @author Thunder.Hsu 2013-6-22
 */
public class Session implements ISession {

    private Map<String, Object> container = Collections.synchronizedMap(new HashMap<String, Object>());
    private String sId;
    private User user = null;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setId(String sId) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAttribute(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public Enumeration getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void invalidate() {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUser(User user) {
        // TODO Auto-generated method stub

    }

}
