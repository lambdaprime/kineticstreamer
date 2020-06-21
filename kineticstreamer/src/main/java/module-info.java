/**
 * Copyright 2020 lambdaprime
 * 
 * Email: id.blackmesa@gmail.com 
 * Website: https://github.com/lambdaprime
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
 * <p>kineticstreamer - Java module to do (de)serialization of Java objects into streams.
 * By default it supports object conversion to stream of bytes and back. But it is extendible
 * to any other format as well.</p>
 * 
 * <h1>Stream</h1>
 * <p>In terms on kineticstreamer Stream represents sequence of any sort (sequence of bytes,
 * words, etc). The type of elements in such sequence don't need to be the same. Streams have flat
 * schemas opposite to JSON, XML, etc.</p>
 * <p>Example of streams are CSV files.</p>
 * 
 * <p>It parses object tree and allows you to get control over how types are going to be
 * (de)serialized.</p>
 * 
 * <p>Only fields annotated with @Streamed annotation will be (de)serialized.</p> 
 * 
 * <h1>Arrays</h1>
 *
 * <p><b>kineticstreamer</b> does not support serialization of arrays of primitive types. If you still
 * need to use primitive type arrays please use their wrapped version (Integer[], Long[], etc).
 *
 * <p>Any array A serialized in following format:</p>
 *
 * kineticDataOutput.writeInt(A.length) | write(a[0]) | ... | write(a[N])
 *
 * <p>Where write call will be dispatched based on item types: if they primitive it will go to
 * KineticDataOutput if they complex then it will go to KineticStreamWriter.</p>
 * 
 */
module id.kineticstreamer {
    exports id.kineticstreamer;
    exports id.kineticstreamer.annotations;
    exports id.kineticstreamer.streams;
    requires id.xfunction;
}