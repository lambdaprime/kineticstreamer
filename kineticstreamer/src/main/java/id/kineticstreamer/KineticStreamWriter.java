package id.kineticstreamer;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public void write(Object b) throws Exception {
        if (b == null) return;
        String typeName = b.getClass().getName();
        switch (typeName) {
        case "java.lang.String": out.writeString((String)b); break;
        case "int":
        case "java.lang.Integer": out.writeInt((Integer)b); break;
        case "float":
        case "java.lang.Float": out.writeFloat((Float)b); break;
        case "double":
        case "java.lang.Double": out.writeDouble((Double)b); break;
        case "boolean":
        case "java.lang.Boolean": out.writeBoolean((Boolean)b); break;
        default: {
            if (typeName.startsWith("[")) {
                var array = (Object[])b;
                out.writeInt(array.length);
                for (var item: array) {
                    write(item);
                }
                break;
            } else {
                utils.findStreamedFields(b.getClass())
                    .map(f -> Unchecked.get(() -> f.get(b)))
                    .forEach(Unchecked.wrapAccept(this::write));
            }
        }
        }
    }
}
