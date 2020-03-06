package id.kineticstreamer;

import java.io.DataOutput;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private DataOutput out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(DataOutput out) {
        this.out = out;
    }

    public void write(Object b) {
        utils.findStreamedFields(b)
            .map(f -> Unchecked.runUnchecked(() -> f.get(b)))
            .forEach(Unchecked.wrapAccept(this::writeFieldValue));
    }

    private void writeFieldValue(Object f) throws Exception {
        switch (f.getClass().getName()) {
        case "java.lang.String": out.writeBytes((String)f); break;
        case "java.lang.Integer": out.writeInt((Integer)f); break;
        default: System.out.println(f.getClass().getName());
        }
    }
}
