/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import id.kineticstreamer.DefaultKineticDataInput;
import id.kineticstreamer.DefaultKineticDataOutput;
import id.kineticstreamer.InputStreamByteList;
import id.kineticstreamer.KineticStreamReader;
import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.OutputStreamByteList;
import id.kineticstreamer.tests.streamed.Book;
import id.kineticstreamer.tests.streamed.StringMessage;

public class KineticStreamTest {

    static Stream<List> dataProvider() {
        return Stream.of(
            List.of("05, 00, 00, 00, 68, 65, 6c, 6c, 6f", new StringMessage("hello")),
            List.of("0b, 00, 00, 00, 50, 68, 69, 6c, 69, 70, 20, 44, 69, 63, 6b, 41, 78, 00, 00, 00, 00, 00, 0b, 40, 3f, 66, 66, 66, 66, 66, 66",
                new Book("Philip Dick", 15.5F, 31.4, 11))
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void testWrite(List testData) {
        var b = testData.get(1);
        OutputStreamByteList collector = new OutputStreamByteList();
        var dos = new DefaultKineticDataOutput(new DataOutputStream(collector));
        var ks = new KineticStreamWriter(dos);
        ks.write(b);
        assertEquals(testData.get(0), collector.asHexString());
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void testRead(List testData) throws Exception {
        var collector = new InputStreamByteList((String)testData.get(0));
        var dis = new DefaultKineticDataInput(new DataInputStream(collector));
        var ks = new KineticStreamReader(dis);
        Object expected = testData.get(1);
        Object actual = expected.getClass().getConstructor().newInstance();
        ks.read(actual);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}
