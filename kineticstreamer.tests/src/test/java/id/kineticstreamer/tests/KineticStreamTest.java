/*
 * Copyright 2020 kineticstreamer project
 * 
 * Website: https://github.com/lambdaprime/kineticstreamer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Authors:
 * - lambdaprime <intid@protonmail.com>
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
import id.kineticstreamer.tests.streamed.DepthFrame;
import id.kineticstreamer.tests.streamed.StringMessage;
import id.xfunction.ResourceUtils;
import id.xfunction.io.XInputStream;
import id.xfunction.io.XOutputStream;

public class KineticStreamTest {

    private static final ResourceUtils resourceUtils = new ResourceUtils();

    static Stream<List> dataProvider() {
        return Stream.of(
            List.of("00, 00, 00, 05, 68, 65, 6c, 6c, 6f", new StringMessage("hello")),
            List.of(resourceUtils.readResource("test1"),
                new Book("aaaaaaaa", 15.5F, 31.4, 11, new Author("bbbbb", 123), true, new Integer[] {9, 9, 9},
                    new int[] {2, 3})),
            List.of("00, 00, 00, 03, 61, 61, 61", "aaa"),
            List.of("00, 00, 00, 0a", 10),
            List.of("00, 00, 00, 03, 00, 00, 00, 01, 00, 00, 00, 01, 00, 00, 00, 01", new Integer[] {1, 1, 1}),
            List.of(resourceUtils.readResource("test2"), new DepthFrame(1024, 680, 1., 2., 3., 4.)),
            List.of(resourceUtils.readResource("test3"),
                new Book("aaaaaaaa", .5F, 0, 2, new Author("bob", 321), true, new Integer[] {1, 2, 3},
                    new int[0], List.of("queen", "rabbit", "knight")))
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
