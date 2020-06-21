/*
 * Copyright 2020 lambdaprime
 */
package id.kineticstreamer;

import java.lang.reflect.Array;

import id.kineticstreamer.utils.KineticUtils;
import id.kineticstreamer.utils.ValueSetter;
import id.xfunction.XUtils;
import id.xfunction.function.ThrowingConsumer;
import id.xfunction.function.Unchecked;

public class KineticStreamReader {

    private InputKineticStream in;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamReader(InputKineticStream in) {
        this.in = in;
    }

    public Object read(Class<?> type) throws Exception {
        Object[] holder = new Object[1];
        read(type, obj -> holder[0] = obj);
        return holder[0];
    }

    private void read(Class<?> type, ThrowingConsumer<Object, Exception> setter) throws Exception {
        switch (type.getName()) {
        case "java.lang.String": setter.accept(in.readString()); break;
        case "int":
        case "java.lang.Integer": setter.accept(in.readInt()); break;
        case "float":
        case "java.lang.Float": setter.accept(in.readFloat()); break;
        case "double":
        case "java.lang.Double": setter.accept(in.readDouble()); break;
        case "boolean":
        case "java.lang.Boolean": setter.accept(in.readBool()); break;
        default: {
            if (type.isArray()) {
                var array = (Object[])Array.newInstance(type.getComponentType(), in.readInt());
                type = type.getComponentType();
                for (int i = 0; i < array.length; i++) {
                    int j = i;
                    read(type, obj -> array[j] = obj);
                }
                setter.accept(array);
                break;
            } else {
                var obj = createObject(type);
                utils.findStreamedFields(type)
                    .forEach(Unchecked.wrapAccept(field -> read(field.getType(),
                        new ValueSetter(obj, field)::set)));
                setter.accept(obj);
            }
        }
    }
    }

    private Object createObject(Class<?> type) throws Exception {
        var ctor = type.getConstructor();
        if (ctor == null)
            XUtils.throwRuntime("Type %s has no default ctor",  type);
        return ctor.newInstance();
    }

}
