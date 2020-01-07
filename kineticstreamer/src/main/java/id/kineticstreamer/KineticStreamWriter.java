package id.kineticstreamer;

import java.io.DataOutput;
import java.util.Arrays;

import id.kineticstreamer.annotations.Streamed;
import id.xfunction.function.Unchecked;

/**
 * ddddddddddd
 */
public class KineticStreamWriter {

    private DataOutput out;

    /**
     * ddddddddddd
     */
    public KineticStreamWriter(DataOutput out) {
        this.out = out;
    }

    /**
     * ddddddddddd
     */
    public void write(Object b) {
        Arrays.stream(b.getClass().getFields())
            .filter(f -> f.getAnnotationsByType(Streamed.class) != null)
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
