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
package id.kineticstreamer.streams;

import java.io.DataOutput;
import java.io.IOException;

import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.OutputKineticStream;

/**
 * Kinetic stream implementation for (de)serialization of Java objects
 * into sequence of bytes and back.
 *
 */
public class ByteOutputKineticStream implements OutputKineticStream {

    private DataOutput out;

    /**
     * Creates byte kinetic stream and attaches it to 'out'
     */
    public ByteOutputKineticStream(DataOutput out) {
        this.out = out;
    }

    @Override
    public void writeString(String str) throws IOException {
        writeInt(str.length());
        out.write(str.getBytes());
    }

    @Override
    public void writeInt(Integer i) throws IOException {
        out.writeInt(i);
    }

    @Override
    public void writeDouble(Double f) throws IOException {
        out.writeDouble(f);
    }

    @Override
    public void writeFloat(Float f) throws IOException {
        out.writeFloat(f);
    }

    @Override
    public void writeBoolean(Boolean b) throws IOException {
        out.writeBoolean(b);
    }

    /**
     * <p>Any array A serialized in following format:</p>
     *
     * writeInt(A.length) | KineticStreamWriter::write(a[0]) | ... | KineticStreamWriter::write(a[N])
     *
     * <p>Where write call for each item will use KineticStreamWriter on same object of
     * kinetic stream (on 'this').</p>
     */
    @Override
    public void writeArray(Object[] array) throws Exception {
        out.writeInt(array.length);
        for (var item: array) {
            new KineticStreamWriter(this).write(item);
        }
    }

    @Override
    public void close() throws Exception {
    }

}
