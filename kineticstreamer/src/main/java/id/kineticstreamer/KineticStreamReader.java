/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.lang.reflect.Field;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamReader {

    private KineticDataInput in;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamReader(KineticDataInput in) {
        this.in = in;
    }

    public void read(Object b) {
        utils.findStreamedFields(b)
            .forEach(Unchecked.wrapAccept(f -> readValue(b, f)));
    }

    private void readValue(Object o, Field field) throws Exception {
        var type = field.getType().getName();
        switch (type) {
        case "java.lang.String": field.set(o, in.readString()); break;
        case "int":
        case "java.lang.Integer": field.set(o, in.readInt()); break;
        case "float":
        case "java.lang.Float": field.set(o, in.readFloat()); break;
        case "double":
        case "java.lang.Double": field.set(o, in.readDouble()); break;
        default: throw new RuntimeException("Unknown type: " + type);
        }
    }
}
