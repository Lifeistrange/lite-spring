package com.lifeiscoding.spring.beans;

public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException;
}
