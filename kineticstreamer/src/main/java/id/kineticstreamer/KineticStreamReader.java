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
        switch (field.getType().getName()) {
        case "java.lang.String": field.set(o, in.readString()); break;
        case "java.lang.Integer":
        case "int": field.set(o, in.readInt()); break;
        default: System.out.println(field.getClass().getName());
        }
    }
}
