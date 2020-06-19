package id.kineticstreamer;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public void write(Object b) {
        utils.findStreamedFields(b)
            .map(f -> Unchecked.get(() -> f.get(b)))
            .forEach(Unchecked.wrapAccept(this::writeFieldValue));
    }

    private void writeFieldValue(Object fieldValue) throws Exception {
        if (fieldValue == null) return;
        String typeName = fieldValue.getClass().getName();
        switch (typeName) {
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
            if (typeName.startsWith("[")) {
                var array = (Object[])fieldValue;
                out.writeInt(array.length);
                for (var item: array) {
                    writeFieldValue(item);
                }
                break;
            } else {
                write(fieldValue);
            }
        }
        }
    }
}
