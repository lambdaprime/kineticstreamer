package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import id.kineticstreamer.DefaultKineticDataOutput;
import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.OutputStreamCollector;

public class KineticStreamWriterTest {

    static Stream<List> dataProvider() {
        return Stream.of(
            List.of(new StringMessage("hello"), "[05, 00, 00, 00, 68, 65, 6c, 6c, 6f]"),
            List.of(new Book("Philip Dick", 15), "[0b, 00, 00, 00, 50, 68, 69, 6c, 69, 70, 20, 44, 69, 63, 6b, 00, 00, 00, 0f]")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void test(List testData) {
        var b = testData.get(0);
        OutputStreamCollector collector = new OutputStreamCollector();
        var dos = new DefaultKineticDataOutput(new DataOutputStream(collector));
        var ks = new KineticStreamWriter(dos);
        ks.write(b);
        assertEquals(testData.get(1), collector.output.toString());
    }
}
