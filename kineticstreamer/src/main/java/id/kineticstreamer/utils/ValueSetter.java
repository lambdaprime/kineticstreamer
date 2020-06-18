/*
 * Copyright 2020 lambdaprime
 */
package id.kineticstreamer.utils;

import java.lang.reflect.Field;

public class ValueSetter {

    private Object obj;
    private Field field;

    public ValueSetter(Object obj, Field field) {
        this.obj = obj;
        this.field = field;
    }

    public void set(Object value) throws Exception {
        Class<?> type = field.getType();
        field.set(obj, type.isArray()? type.cast(value): value);
    }

}