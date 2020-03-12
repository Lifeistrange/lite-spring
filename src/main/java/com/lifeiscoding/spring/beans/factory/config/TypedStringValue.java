package com.lifeiscoding.spring.beans.factory.config;

public class TypedStringValue {
    private String attributeValue;

    public TypedStringValue(String attributeValue) {

        this.attributeValue = attributeValue;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
