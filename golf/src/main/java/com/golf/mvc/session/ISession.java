package com.golf.mvc.session;

import java.util.Enumeration;

import com.golf.rbac.entity.User;

public interface ISession {

    public String getId();

    public void setId(String sId);

    public <T> T getAttribute(String name);

    public void setAttribute(String name, Object value);

    public void removeAttribute(String name);

    public Enumeration getAttributeNames();

    public void invalidate();

    //
    public User getUser();

    public void setUser(User user);

}
