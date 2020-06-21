/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import id.kineticstreamer.KineticStreamReader;
import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.streams.ByteInputKineticStream;
import id.kineticstreamer.streams.ByteOutputKineticStream;
import id.kineticstreamer.tests.streamed.Author;
import id.kineticstreamer.tests.streamed.Book;
import id.kineticstreamer.tests.streamed.StringMessage;
import id.xfunction.XUtils;
import id.xfunction.io.XInputStream;
import id.xfunction.io.XOutputStream;

public class KineticStreamTest {

    static Stream<List> dataProvider() {
        return Stream.of(
            List.of("00, 00, 00, 05, 68, 65, 6c, 6c, 6f", new StringMessage("hello")),
            List.of(XUtils.readResource("test1"),
                new Book("aaaaaaaa", 15.5F, 31.4, 11, new Author("bbbbb", 123), true, new Integer[] {9, 9, 9})),
            List.of("00, 00, 00, 03, 61, 61, 61", "aaa"),
            List.of("00, 00, 00, 0a", 10),
            List.of("00, 00, 00, 03, 00, 00, 00, 01, 00, 00, 00, 01, 00, 00, 00, 01", new Integer[] {1, 1, 1})
        );
    }//

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void testWrite(List testData) throws Exception {
        var b = testData.get(1);
        XOutputStream collector = new XOutputStream();
        var dos = new ByteOutputKineticStream(new DataOutputStream(collector));
        var ks = new KineticStreamWriter(dos);
        ks.write(b);
        assertEquals(testData.get(0), collector.asHexString());
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void testRead(List testData) throws Exception {
        var collector = new XInputStream((String)testData.get(0));
        var dis = new ByteInputKineticStream(new DataInputStream(collector));
        var ks = new KineticStreamReader(dis);
        Object expected = testData.get(1);
        Object actual = ks.read(expected.getClass());
        System.out.println(actual);
        if (expected.getClass().isArray())
            assertArrayEquals((Object[])expected, (Object[])actual);
        else
            assertEquals(expected, actual);
    }
}
