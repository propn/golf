/**
 * 
 */
package com.golf.mvc.view;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thunder.Hsu
 * 
 */
public class View {

    private Vkind vkind;
    private String path;
    private Map<String, Object> model = new HashMap<String, Object>();

    public View(Vkind vkind, String path, Map<String, Object> model) {
        this.vkind = vkind;
        this.path = path;
        if (null != model) {
            this.model = model;
        }
    }

    public String getPath() {
        return path;
    }

    public Vkind getKind() {
        return vkind;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void addAttribute(String key, Object value) {
        model.put(key, value);
    }
}
