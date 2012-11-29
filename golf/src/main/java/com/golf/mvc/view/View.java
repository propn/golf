/**
 * 
 */
package com.golf.mvc.view;

import java.util.Map;
import java.util.Map.Entry;

import com.golf.tools.MultMap;

/**
 * @author Thunder.Hsu
 * 
 */
public class View {

    private Vkind vkind;
    private String path;
    private MultMap<String, Object> model = new MultMap<String, Object>();

    public View(String path) {
        this.vkind = Vkind.forward;
        this.path = path;
    }

    public View(Vkind vkind, String path) {
        this.vkind = vkind;
        this.path = path;
    }

    public View(Vkind vkind, String path, Map<String, Object> map) {
        this.vkind = vkind;
        this.path = path;
        if (null != model) {
            for (Entry<String, Object> p : map.entrySet()) {
                model.put(p.getKey(), p.getValue());
            }
        }
    }

    public View(Vkind vkind, String path, MultMap<String, Object> model) {
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

    public MultMap<String, Object> getModel() {
        return model;
    }

    public void addAttribute(String key, Object value) {
        model.put(key, value);
    }
}
