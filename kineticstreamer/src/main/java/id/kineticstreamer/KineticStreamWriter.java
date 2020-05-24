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

    private void writeFieldValue(Object f) throws Exception {
        switch (f.getClass().getName()) {
        case "java.lang.String": out.writeString((String)f); break;
        case "java.lang.Integer": out.writeInt((Integer)f); break;
        default: System.out.println(f.getClass().getName());
        }
    }
}
