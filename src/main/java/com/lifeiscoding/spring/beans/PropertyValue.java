package com.lifeiscoding.spring.beans;

public class PropertyValue {
    private String name;
    private Object value;
    private boolean converted = false;
    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }

    public synchronized Object getConvertedValue() {
        return convertedValue;
    }

    public synchronized void setConvertedValue(Object value) {
        this.converted = true;
        this.convertedValue = value;
    }
}
