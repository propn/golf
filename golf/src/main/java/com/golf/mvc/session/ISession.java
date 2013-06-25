package com.golf.mvc.session;

import java.io.Serializable;

import com.golf.rbac.entity.User;

public interface ISession extends Serializable {

    public String getSessionId();

    public void setSessionId(String sessionId);

    /**
     * 当前用户
     * 
     * @return
     */
    public User getUser();

    public void setUser(User user);

    /**
     * 
     * @param key
     * @return
     */
    public <T> T get(String key);

    void put(String key, Object val);

}
