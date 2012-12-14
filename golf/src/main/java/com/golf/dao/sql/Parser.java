package com.golf.dao.sql;

public interface Parser {
    public Object[] parse(String sql, final Object param) throws Exception;
}
