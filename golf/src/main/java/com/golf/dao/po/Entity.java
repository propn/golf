/**
 * 
 */
package com.golf.dao.po;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.golf.utils.JaxbUtils;
import com.golf.utils.RefUtils;
import com.golf.utils.json.Json;

/**
 * @author Thunder.Hsu
 * 
 */
public abstract class Entity implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 属性
    public Object get(String fieldName) throws Exception {
        Object value = RefUtils.getFieldValue(this, fieldName);
        return value;
    }

    public void set(String fieldName, Object value) throws Exception {
        RefUtils.setFieldValue(this, fieldName, value);
    }

    public void set(Map<String, Object> map) throws Exception {
        Set<String> fieldNames = map.keySet();
        for (String fieldName : fieldNames) {
            RefUtils.setFieldValue(this, fieldName, map.get(fieldName));
        }
    }

    public Map<String, Object> toMap() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Field> fields = RefUtils.getFields(this.getClass());
        for (Iterator<Field> it = fields.values().iterator(); it.hasNext();) {
            Field field = (Field) it.next();
            Object v = field.get(this);
            if (null == v) {
                continue;
            }
            if (v instanceof Entity) {
                map.put(field.getName(), ((Entity) v).toMap());
            } else {
                // 默认值处理
                if (field.getType() == long.class || field.getType() == int.class || field.getType() == double.class) {
                    if (String.valueOf(v).equals("0")) {
                        continue;
                    }
                }
                map.put(field.getName(), v);
            }
        }
        return map;
    }

    // 序列化工具
    public String toJson() {
        return Json.toJson(this);
    }

    public String toXml() throws JAXBException, IOException, ClassNotFoundException {
        return JaxbUtils.toXml(this);
    }

    // 数据库操作CRUD
    public void save() throws Exception {
        EntityUtils.intsert(this);
    }

    public <T extends Entity> T getOne() throws Exception {
        List<T> pos = qryList();
        return null != pos && pos.size() > 0 ? pos.get(0) : null;
    }

    /**
     * 模版equel查询
     * 
     * @param <T>
     * 
     * @param T
     * 
     * @param <T>
     * 
     * @param <T>
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> qryList() throws Exception {
        return (List<T>) EntityUtils.qryAll(this);
    }

    /**
     * 动态查询
     * 
     * @param <T>
     * 
     * @param qryCode 配置Sql编码
     * @param param 模版入参
     * @return
     */
    public <T> List<T> query(String qryCode, Object param) {
        return null;
    }

    public int update() throws Exception {
        return EntityUtils.update(this);
    }

    public int delete() throws Exception {
        return EntityUtils.delete(this);
    }

}
