package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataOutputStream;

import org.junit.jupiter.api.Test;

import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.OutputStreamCollector;

public class KineticStreamWriterTest {

    @Test
    public void test() {
        var b = new Book();
        b.name = "Philip Dick";
        b.price = 15;
        OutputStreamCollector collector = new OutputStreamCollector();
        var dos = new DataOutputStream(collector);
        var ks = new KineticStreamWriter(dos);
        ks.write(b);
        assertEquals("[50, 68, 69, 6c, 69, 70, 20, 44, 69, 63, 6b, 00, 00, 00, 0f]",
            collector.output.toString());
    }
}
