/**
 * 
 */
package com.golf.mvc.session.imp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.golf.mvc.session.ISession;
import com.golf.rbac.entity.User;

/**
 * 
 * @author Thunder.Hsu 2013-6-22
 */
public class Session implements ISession {

    private Map<String, Object> container = Collections.synchronizedMap(new HashMap<String, Object>());

    private String sessionId;
    private User user = null;

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) this.container.get(key);
    }

    @Override
    public void put(String key, Object val) {
        this.container.put(key, val);
    }

}
