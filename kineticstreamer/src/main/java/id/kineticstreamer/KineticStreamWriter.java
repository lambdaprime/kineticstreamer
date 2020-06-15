package id.kineticstreamer;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private KineticDataOutput out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(KineticDataOutput out) {
        this.out = out;
    }

    public void write(Object b) {
        utils.findStreamedFields(b)
            .map(f -> Unchecked.get(() -> f.get(b)))
            .forEach(Unchecked.wrapAccept(this::writeFieldValue));
    }

    private void writeFieldValue(Object fieldValue) throws Exception {
        if (fieldValue == null) return;
        switch (fieldValue.getClass().getName()) {
        case "java.lang.String": out.writeString((String)fieldValue); break;
        case "int":
        case "java.lang.Integer": out.writeInt((Integer)fieldValue); break;
        case "float":
        case "java.lang.Float": out.writeFloat((Float)fieldValue); break;
        case "double":
        case "java.lang.Double": out.writeDouble((Double)fieldValue); break;
        case "boolean":
        case "java.lang.Boolean": out.writeBoolean((Boolean)fieldValue); break;
        default: {
            write(fieldValue);
        }
        }
    }
}
