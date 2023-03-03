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
/**
 * <b>kineticstreamer</b> - Java module to do (de)serialization of Java objects into any type of
 * streams. It provides default implementations for object conversion to stream of bytes and CSV
 * files. But you can add support for any other format as well.
 *
 * <p>It parses object tree and allows you to get control over how types are going to be
 * (de)serialized.
 *
 * <h2>Why not Java standard ObjectOutputStream</h2>
 *
 * <p>By default {@link java.io.ObjectOutputStream} adds to stream {@link
 * java.io.ObjectStreamConstants#TC_BLOCKDATA}:
 *
 * <pre>{@code
 * var fos = new FileOutputStream("t.tmp");
 * var oos = new ObjectOutputStream(fos) {
 *     @Override
 *     protected void writeStreamHeader() throws java.io.IOException {
 *         // ignore header
 *     }
 * };
 *
 * oos.writeInt(0xffff);
 * oos.close();
 * }</pre>
 *
 * <p>Will put to stream:
 *
 * <pre>TC_BLOCKDATA|0x0000ffff</pre>
 *
 * <p>It is possible to use ObjectOutputStream default constructor to a avoid this but it requires
 * to reimplement writeObject which does all work with class tree serialization (behind private
 * method writeObject0 which you cannot access from writeObject because it is final).
 *
 * <h2>Stream</h2>
 *
 * <p>In terms of <b>kineticstreamer</b> 'stream' represents sequence of any sort (sequence of
 * bytes, words, numbers, etc). The type of elements in such sequence don't need to be the same.
 * Streams have flat schemas opposite to JSON, XML, etc.
 *
 * <p>Example of streams are flat byte formats, CSV files, etc.
 *
 * <h2>Kinetic stream</h2>
 *
 * <p>Kinetic stream is an abstraction which is defined by two interfaces:
 *
 * <ul>
 *   <li>{@link id.kineticstreamer.InputKineticStream}
 *   <li>{@link id.kineticstreamer.OutputKineticStream}
 * </ul>
 *
 * <p><b>kineticstreamer</b> relies on them for Java objects (de)serialization.
 *
 * <p>Kinetic streams allow <b>kineticstreamer</b> to operate with any type of streams without even
 * knowing anything about their format.
 *
 * <p>Every type of kinetic stream must have its own implementation of {@link
 * id.kineticstreamer.InputKineticStream} (for reading objects from it) and {@link
 * id.kineticstreamer.OutputKineticStream} for writing them).
 *
 * <p><b>kineticstreamer</b> comes with some predefined kinetic streams (see {@link
 * id.kineticstreamer.streams}). To add support for custom types of streams you need to implement
 * kinetic stream interfaces for them.
 *
 * <h2>Streamed classes</h2>
 *
 * <p>Streamed classes are classes which objects can be (de)serialized.
 *
 * <p>Streamed fields - fields of a streamed classes which are going to be (de)serialized.
 *
 * <h3>Field types</h3>
 *
 * <b>kineticstreamer</b> divides all types into two categories:
 *
 * <ul>
 *   <li>kinetic stream types - these are all Java primitive types plus any other types for which
 *       there are separate method definition in {@link id.kineticstreamer.InputKineticStream} and
 *       {@link id.kineticstreamer.OutputKineticStream}
 *   <li>foreign types - all others types (mostly these are classes which are composed from fields
 *       of kineticstream types or other foreign types).
 * </ul>
 *
 * <p>Understanding this separation is important for creating streamed classes and controllers.
 *
 * <h2>Defining streamed classes</h2>
 *
 * <p>Streamed classes must satisfy following requirements:
 *
 * <ul>
 *   <li>Streamed class must have default constructor
 *   <li>Streamed fields must be public. Any static, private, final, transient fields are ignored.
 *   <li>Streamed fields of foreign types must not be null (unless you implement controllers and
 *       handle (de)serialization of such types manually). This is required for deserialization
 *       purposes. By default during deserialization {@link id.kineticstreamer.KineticStreamReader}
 *       expects all streamed fields of foreign types be present in the stream. It is a good
 *       practice to initialize such fields with their default constructor.
 * </ul>
 *
 * <h2>Controllers</h2>
 *
 * <p>Controllers help in case of complex serialization logic of certain foreign field types. For
 * example:
 *
 * <ul>
 *   <li>they allow to support polymorphic fields which type is undefined until certain flags are
 *       read from the stream
 *   <li>when certain fields may be present in the stream or not based on data which was previously
 *       read from it
 * </ul>
 *
 * <p>Controllers are defined in {@link id.kineticstreamer.KineticStreamReaderController} and {@link
 * id.kineticstreamer.KineticStreamWriterController}. Users can extend them to provide custom logic
 * and inject into {@link id.kineticstreamer.KineticStreamReader} and {@link
 * id.kineticstreamer.KineticStreamWriter} correspondingly.
 *
 * <h2>Examples</h2>
 *
 * <p>Streamed class:
 *
 * <pre>{@code
 * public class StringMessage {
 *
 *     public String data;
 *
 *     // streamed classes require default ctor
 *     public StringMessage() {
 *     }
 *
 *     public StringMessage(String data) {
 *         this.data = data;
 *     }
 * }
 * }</pre>
 *
 * Streaming StringMessage object to stream of bytes and back:
 *
 * <pre>{@code
 * // write
 * var tmpFile = Files.createTempFile("", "kineticstream");
 * var fos = new FileOutputStream(tmpFile.toFile());
 * var dos = new ByteOutputKineticStream(new DataOutputStream(fos));
 * var ksw = new KineticStreamWriter(dos);
 * ksw.write(new StringMessage("hello kineticstreamer"));
 *
 * // read back
 * var fis = new FileInputStream(tmpFile.toFile());
 * var dis = new ByteInputKineticStream(new DataInputStream(fis));
 * var ksr = new KineticStreamReader(dis);
 * StringMessage actual = (StringMessage) ksr.read(StringMessage.class);
 * System.out.println(actual.data);
 * }</pre>
 *
 * @see <a href="https://github.com/lambdaprime/kineticstreamer/releases">Download
 *     kineticstreamer</a>
 * @see <a href="https://github.com/lambdaprime/kineticstreamer">Github</a>
 * @author lambdaprime intid@protonmail.com
 */
module id.kineticstreamer {
    exports id.kineticstreamer;
    exports id.kineticstreamer.streams;

    requires id.xfunction;
}
