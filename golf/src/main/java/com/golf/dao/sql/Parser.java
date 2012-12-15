package com.golf.dao.sql;

import java.util.Map;

public interface Parser {
    public Object[] parse(String sql, Map<String, Object> params) throws Exception;
}
