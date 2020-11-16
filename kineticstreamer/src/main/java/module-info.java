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
/**
 * <p><b>kineticstreamer</b> - Java module to do (de)serialization of Java objects into streams.
 * By default it supports object conversion to stream of bytes and back. But it is extendible
 * to any other format as well.</p>
 * <p>It parses object tree and allows you to get control over how types are going to be
 * (de)serialized.</p>
 * <p>Only fields annotated with @Streamed annotation will be (de)serialized.</p> 
 * 
 * <h1>Stream</h1>
 * <p>In terms of <b>kineticstreamer</b> 'stream' represents sequence of any sort (sequence of bytes,
 * words, numbers, etc). The type of elements in such sequence don't need to be the same. Streams have flat
 * schemas opposite to JSON, XML, etc.</p>
 * <p>Example of streams are flat byte formats, CSV files, etc.</p>
 * 
 * <h1>Arrays</h1>
 * <p><b>kineticstreamer</b> does not support serialization of arrays of primitive types. If you still
 * need to use primitive type arrays please use their wrapped version (Integer[], Long[], etc).</p>
 *
 * <h1>Examples</h1>
 * 
 * <p>Streamed class:</p>
 * 
 * <pre><code>
public class StringMessage {

    &#64;Streamed
    public String data;

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
 *
 */
module id.kineticstreamer {
    exports id.kineticstreamer;
    exports id.kineticstreamer.annotations;
    exports id.kineticstreamer.streams;
    requires id.xfunction;
}