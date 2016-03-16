package com.myspring.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class Bean implements Serializable{
    private String id;
    private String className;
    private List<Property> properties = new ArrayList<Property>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}
