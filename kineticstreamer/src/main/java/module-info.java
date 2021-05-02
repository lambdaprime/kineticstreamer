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
 * objects into streams. It provides default implementations for object
 * conversion to stream of bytes and CSV files. But you can add support
 * to any other format as well.</p>
 * 
 * <p>It parses object tree and allows you to get control over how types
 * are going to be (de)serialized.</p>
 * 
 * <p>Only fields annotated with {@link id.kineticstreamer.annotations.Streamed} annotation will be (de)serialized.</p>
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
 * <p>Every type of stream has its own implementation of kinetic stream ifaces.</p>
 * 
 * <p><b>kineticstreamer</b> comes with some predefined kinetic streams
 * (see {@link id.kineticstreamer.streams}). To add support for custom types of
 * streams you need to implement kinetic stream ifaces for them.</p> 
 * 
 * <h1>Arrays</h1>
 * <p><b>kineticstreamer</b> supports (de)serialization of arrays with non-primitive types
 * plus some primitive types as well. If you need to use arrays with primitive type which are not
 * yet supported please use their wrapped version (Short[], etc).</p>
 * 
 * <h1>Defining streamed classes</h1>
 * 
 * <p>These are the classes which are going to be (de)serialized with their fields being
 * streamed. There are certain requirements to such classes:</p>
 * 
 * <ul>
 * <li>Need to define default ctor</li>
 * <li>Fields which will be (de)serialized (streamed fields) should be annotated
 * with {@link id.kineticstreamer.annotations.Streamed} annotation</li>
 * <li>Streamed fields should be accessible to the <b>kineticstreamer</b> or be public</li>
 * <li>Streamed fields which point to arrays should be initialized with empty arrays</li>
 * <li>If the type of streamed field is yet another streamed class then such field
 * should be initialized with non null value (i.e. with its default ctor) otherwise the
 * <b>kineticstreamer</b> will ignore such fields during streaming with all its subfields</li>
 * </ul>
 *
 * <h1>Examples</h1>
 * 
 * <p>Streamed class:</p>
 * 
 * <pre><code>
public class StringMessage {

    &#64;Streamed
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
 * Streaming:
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
    exports id.kineticstreamer.annotations;
    exports id.kineticstreamer.streams;
    requires id.xfunction;
}