package com.golf.dao.sql;

public interface Filter {
    public Object[] doFilter(String sql, final Object param) throws Exception;
}
