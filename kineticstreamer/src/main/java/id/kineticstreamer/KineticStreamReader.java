/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.lang.reflect.Field;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.XUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamReader {

    private KineticDataInput in;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamReader(KineticDataInput in) {
        this.in = in;
    }

    public void read(Object obj) {
        utils.findStreamedFields(obj)
            .forEach(Unchecked.wrapAccept(f -> readValue(obj, f)));
    }

    private void readValue(Object obj, Field field) throws Exception {
        var type = field.getType().getName();
        switch (type) {
        case "java.lang.String": field.set(obj, in.readString()); break;
        case "int":
        case "java.lang.Integer": field.set(obj, in.readInt()); break;
        case "float":
        case "java.lang.Float": field.set(obj, in.readFloat()); break;
        case "double":
        case "java.lang.Double": field.set(obj, in.readDouble()); break;
        case "boolean":
        case "java.lang.Boolean": field.set(obj, in.readBool()); break;
        default: {
            var ctor = field.getType().getConstructor();
            if (ctor == null)
                XUtils.throwRuntime("Type %s has no default ctor",  type);
            var innerObj = ctor.newInstance();
            read(innerObj);
            field.set(obj, innerObj);
        }
        }
    }
}
