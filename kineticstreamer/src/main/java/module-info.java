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
/**
 * <p><b>kineticstreamer</b> - Java module to do (de)serialization of Java
 * objects into any type of streams. It provides default implementations for object
 * conversion to stream of bytes and CSV files. But you can add support
 * for any other format as well.</p>
 * 
 * <p>It parses object tree and allows you to get control over how types
 * are going to be (de)serialized.</p>
 * 
 * <h1>Stream</h1>
 * <p>In terms of <b>kineticstreamer</b> 'stream' represents sequence of any
 * sort (sequence of bytes, words, numbers, etc). The type of elements in such
 * sequence don't need to be the same. Streams have flat schemas opposite to
 * JSON, XML, etc.</p>
 * 
 * <p>Example of streams are flat byte formats, CSV files, etc.</p>
 * 
 * <h1>Kinetic stream</h1>
 * <p>Kinetic stream is an abstraction which is defined by two interfaces:</p>
 * <ul>
 * <li>{@link id.kineticstreamer.InputKineticStream}</li>
 * <li>{@link id.kineticstreamer.OutputKineticStream}</li>
 * </ul>
 * <p><b>kineticstreamer</b> relies on them for Java objects (de)serialization.</p>
 * 
 * <p>Kinetic streams allow <b>kineticstreamer</b> to operate with any type of
 * streams without even knowing anything about their format.</p>
 * 
 * <p>Every type of kinetic stream must have its own implementation of {@link id.kineticstreamer.InputKineticStream}
 * (for reading objects from it) and {@link id.kineticstreamer.OutputKineticStream} for writing them).</p>
 * 
 * <p><b>kineticstreamer</b> comes with some predefined kinetic streams
 * (see {@link id.kineticstreamer.streams}). To add support for custom types of
 * streams you need to implement kinetic stream interfaces for them.</p> 
 * 
 * <h1>Streamed classes</h1>
 * 
 * <p>Streamed classes are classes which objects are going to be (de)serialized. There are certain requirements to such classes:</p>
 * 
 * <ul>
 * <li>Streamed class must have default ctor</li>
 * <li>Streamed fields (fields which are going to be (de)serialized) should be public. Any static, private, final,
 * transient fields are ignored.</li>
 * </ul>
 * 
 * <h2>Field types</h2>
 * <b>kineticstreamer</b> divides all types into two categories:
 * <ul>
 * <li>kinetic stream types - these are all Java primitive types plus any other types for which there are separate
 * method definition in {@link id.kineticstreamer.InputKineticStream} and {@link id.kineticstreamer.OutputKineticStream}
 * <li>foreign types - all non kinetic stream types
 * </ul>
 * <p>Understanding this separation is important for writing controllers described below. 
 * 
 * <h1>Arrays</h1>
 * <p><b>kineticstreamer</b> supports (de)serialization of arrays with non-primitive types
 * plus some primitive types as well. If you need to use arrays with primitive type which are not
 * yet supported please use their wrapped version (Short[], etc).</p>
 * 
 * <h1>Controllers</h1>
 * 
 * <p>Controllers help in case of complex serialization logic of certain foreign field types.
 * For example:
 * <ul>
 * <li>they allow to support polymorphic fields which type is undefined until certain flags
 * are read from the stream
 * <li>when certain fields may be present in the stream or not based on data which was previously read
 * from it
 * </ul>
 * <p>Controllers defined in {@link id.kineticstreamer.KineticStreamReaderController} and
 * {@link id.kineticstreamer.KineticStreamWriterController}. Users can extend them to provide custom
 * logic and inject into {@link id.kineticstreamer.KineticStreamReader} and {@link id.kineticstreamer.KineticStreamWriter} correspondingly. 
 *
 * <h1>Examples</h1>
 * 
 * <p>Streamed class:</p>
 * 
 * <pre><code>
public class StringMessage {

    public String data;

    // streamed objects require default ctor
    public StringMessage() {
    }

    public StringMessage(String data) {
        this.data = data;
    }

}
 * </code></pre>
 *
 * Streaming StringMessage object to stream of bytes and back:
 *
 * <pre>{@code
// write
var tmpFile = Files.createTempFile("", "kineticstream");
var fos = new FileOutputStream(tmpFile.toFile());
var dos = new ByteOutputKineticStream(new DataOutputStream(fos));
var ksw = new KineticStreamWriter(dos);
ksw.write(new StringMessage("hello kineticstreamer"));

// read back
var fis = new FileInputStream(tmpFile.toFile());
var dis = new ByteInputKineticStream(new DataInputStream(fis));
var ksr = new KineticStreamReader(dis);
StringMessage actual = (StringMessage) ksr.read(StringMessage.class);
System.out.println(actual.data);
 * }</pre>
 *
 * @see <a href="https://github.com/lambdaprime/kineticstreamer/releases">Download kineticstreamer</a>
 * @see <a href="https://github.com/lambdaprime/kineticstreamer">Github</a>
 *
 */
module id.kineticstreamer {
    exports id.kineticstreamer;
    exports id.kineticstreamer.streams;
    requires id.xfunction;
}