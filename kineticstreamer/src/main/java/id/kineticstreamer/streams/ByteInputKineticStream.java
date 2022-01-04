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
package id.kineticstreamer.streams;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import id.kineticstreamer.InputKineticStream;
import id.kineticstreamer.KineticStreamReader;

/**
 * Kinetic stream implementation for deserialization of Java objects
 * into sequence of bytes.
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
    public Object[] readArray(Object[] a, Class<?> type) throws Exception {
        var array = (Object[])Array.newInstance(type, in.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = new KineticStreamReader(this).read(type);
        }
        return array;
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public byte readByte() throws Exception {
        return in.readByte();
    }

    @Override
    public int[] readIntArray(int[] a) throws Exception {
        var array = new int[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readInt();
        }
        return array;
    }

    @Override
    public byte[] readByteArray(byte[] a) throws Exception {
        var array = new byte[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readByte();
        }
        return array;
    }

    @Override
    public double[] readDoubleArray(double[] a) throws Exception {
        var array = new double[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readDouble();
        }
        return array;
    }

    @Override
    public boolean[] readBooleanArray(boolean[] a) throws Exception {
        var array = new boolean[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readBool();
        }
        return array;
    }

    @Override
    public long readLong() throws Exception {
        return in.readLong();
    }

    @Override
    public long[] readLongArray(long[] a) throws Exception {
        var array = new long[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readLong();
        }
        return array;
    }

    @Override
    public short readShort() throws Exception {
        return in.readShort();
    }

    @Override
    public short[] readShortArray(short[] a) throws Exception {
        var array = new short[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readShort();
        }
        return array;
    }

    @Override
    public List readList(List list, Class<?> genericType) {
        throw new RuntimeException("Not supported");
    }

}
