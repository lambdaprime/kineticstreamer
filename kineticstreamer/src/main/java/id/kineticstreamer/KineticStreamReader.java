/*
 * Copyright 2020 lambdaprime
 */
package id.kineticstreamer;

import id.kineticstreamer.utils.ValueSetter;
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
                type = type.getComponentType();
                setter.accept(in.readArray(type));
                break;
            } else {
                var obj = utils.createObject(type);
                utils.findStreamedFields(type)
                    .forEach(Unchecked.wrapAccept(field -> read(field.getType(),
                        new ValueSetter(obj, field)::set)));
                setter.accept(obj);
            }
        }
        }
    }

}
