/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataInputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import id.kineticstreamer.DefaultKineticDataInput;
import id.kineticstreamer.InputStreamByteList;
import id.kineticstreamer.KineticStreamReader;
import id.kineticstreamer.tests.streamed.Book;
import id.kineticstreamer.tests.streamed.StringMessage;

public class KineticStreamReaderTest {

    static Stream<List> dataProvider() {
        return Stream.of(
            List.of("05, 00, 00, 00, 68, 65, 6c, 6c, 6f", new StringMessage("hello")),
            List.of("0b, 00, 00, 00, 50, 68, 69, 6c, 69, 70, 20, 44, 69, 63, 6b, 00, 00, 00, 0f, 00, 00, 00, 0b",
                new Book("Philip Dick", 15, 11))
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void test(List testData) throws Exception {
        var b = testData.get(0);
        var collector = new InputStreamByteList((String)testData.get(0));
        var dis = new DefaultKineticDataInput(new DataInputStream(collector));
        var ks = new KineticStreamReader(dis);
        Object expected = testData.get(1);
        Object actual = expected.getClass().getConstructor().newInstance();
        ks.read(actual);
        assertEquals(expected, actual);
    }
}
