package com.golf.dao;

import java.sql.Timestamp;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.EntityHelper;
import com.golf.dao.entity.IEntity;


/**
 * 
 */
@Table(name = "sysNetProxyCfg")
public class SysNetProxyCfg implements IEntity
{

    @Id
    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "proxyHostIp")
    private String proxyHostIp;

    @Column(name = "proxyPort")
    private Integer proxyPort;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getProxyHostIp()
    {
        return proxyHostIp;
    }

    public void setProxyHostIp(String proxyHostIp)
    {
        this.proxyHostIp = proxyHostIp;
    }

    public Integer getProxyPort()
    {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort)
    {
        this.proxyPort = proxyPort;
    }

    public SysNetProxyCfg(Integer id, Timestamp createDate,
            Timestamp modifyDate, String name, String type, String proxyHostIp,
            Integer proxyPort)
    {
        this.name = name;
        this.type = type;
        this.proxyHostIp = proxyHostIp;
        this.proxyPort = proxyPort;
    }

    public SysNetProxyCfg()
    {
        super();
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
