package id.kineticstreamer;

import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public void write(Object b) throws Exception {
        if (b == null) return;
        var type = b.getClass();
        switch (type.getName()) {
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
            if (type.isArray()) {
                out.writeArray((Object[]) b);
                break;
            } else {
                utils.findStreamedFields(type).stream()
                    .map(f -> Unchecked.get(() -> f.get(b)))
                    .forEach(Unchecked.wrapAccept(this::write));
            }
        }
        }
    }
}
