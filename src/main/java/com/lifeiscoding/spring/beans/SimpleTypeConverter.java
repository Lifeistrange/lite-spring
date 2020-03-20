package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.propertyeditors.CustomBooleanEditor;
import com.lifeiscoding.spring.beans.propertyeditors.CustomNumberEditor;
import com.lifeiscoding.spring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {

    }

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requireType, value)) {
            return (T) value;
        }
        if (value instanceof String) {
            PropertyEditor editor = findDefaultEditor(requireType);
            try {
                editor.setAsText((String) value);
            } catch (IllegalArgumentException e) {
                throw new TypeMismatchException(value, requireType);
            }
            return (T) editor.getValue();
        }
        throw new RuntimeException("Todo: can't convert value for" + value + " class:" + requireType);
    }

    private PropertyEditor findDefaultEditor(Class<?> requireType) {
        PropertyEditor editor = this.getDefaultEditor(requireType);
        if (editor == null){
            throw new RuntimeException("Editor for " + requireType + " has not been implemented");
        }
        return editor;
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if (this.defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);

        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));

    }
}
