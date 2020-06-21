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

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;

import id.kineticstreamer.InputKineticStream;
import id.kineticstreamer.KineticStreamReader;

/**
 * Kinetic stream implementation for (de)serialization of Java objects
 * into sequence of bytes and back.
 *
 */
public class ByteInputKineticStream implements InputKineticStream {

    private DataInput in;

    /**
     * Creates byte kinetic stream and attaches it to 'in'
     */
    public ByteInputKineticStream(DataInput in) {
        this.in = in;
    }

    @Override
    public String readString() throws IOException {
        int len = readInt();
        byte[] b = new byte[len];
        in.readFully(b);
        return new String(b);
    }

    @Override
    public int readInt() throws IOException {
        return in.readInt();
    }

    @Override
    public float readFloat() throws IOException {
        return in.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return in.readDouble();
    }

    @Override
    public boolean readBool() throws IOException {
        return in.readBoolean();
    }

    @Override
    public Object[] readArray(Class<?> type) throws Exception {
        var array = (Object[])Array.newInstance(type, in.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = new KineticStreamReader(this).read(type);
        }
        return array;
    }

}
