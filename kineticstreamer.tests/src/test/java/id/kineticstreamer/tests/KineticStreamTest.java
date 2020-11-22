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
 * - lambdaprime <id.blackmesa@gmail.com>
 */
package id.kineticstreamer.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import id.kineticstreamer.KineticStreamReader;
import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.streams.ByteInputKineticStream;
import id.kineticstreamer.streams.ByteOutputKineticStream;
import id.kineticstreamer.streams.CsvInputKineticStream;
import id.kineticstreamer.streams.CsvOutputKineticStream;
import id.kineticstreamer.tests.streamed.Author;
import id.kineticstreamer.tests.streamed.AuthorsCatalog;
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
    
    @Test
    public void testByteExample() throws Exception {
        var tmpFile = Files.createTempFile("", "kineticstream");
        var fos = new FileOutputStream(tmpFile.toFile());
        var dos = new ByteOutputKineticStream(new DataOutputStream(fos));
        var ksw = new KineticStreamWriter(dos);
        ksw.write(new StringMessage("hello kineticstreamer"));
        var fis = new FileInputStream(tmpFile.toFile());
        var dis = new ByteInputKineticStream(new DataInputStream(fis));
        var ksr = new KineticStreamReader(dis);
        StringMessage actual = (StringMessage) ksr.read(StringMessage.class);
        System.out.println(actual.data);
        assertEquals("hello kineticstreamer", actual.data);
    }
    
    @Test
    public void testCsvExample() throws Exception {
        var tmpFile = Files.createTempFile("", "kineticstream");
        var fos = new FileWriter(tmpFile.toFile());
        var out = new CsvOutputKineticStream(new BufferedWriter(fos));
        var ksw = new KineticStreamWriter(out);
        var expected = new AuthorsCatalog(new Author[] {
            new Author("Unknown", 123),
            new Author("David Deutsch", 2121)
        });
        ksw.write(expected);
        out.close();
        var fis = new FileReader(tmpFile.toFile());
        var in = new CsvInputKineticStream(new BufferedReader(fis));
        var ksr = new KineticStreamReader(in);
        
        AuthorsCatalog actual = (AuthorsCatalog) ksr.read(AuthorsCatalog.class);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}
