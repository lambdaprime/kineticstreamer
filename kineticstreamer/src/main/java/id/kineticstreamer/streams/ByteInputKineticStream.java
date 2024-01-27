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
package id.kineticstreamer.streams;

import id.kineticstreamer.InputKineticStream;
import id.kineticstreamer.KineticStreamReader;
import java.io.DataInput;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

/**
 * Kinetic stream implementation for deserialization of Java objects into sequence of bytes.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class ByteInputKineticStream implements InputKineticStream {

    private DataInput in;

    /** Creates byte kinetic stream and attaches it to 'in' */
    public ByteInputKineticStream(DataInput in) {
        this.in = in;
    }

    @Override
    public String readString(Annotation[] fieldAnnotations) throws IOException {
        int len = in.readInt();
        byte[] b = new byte[len];
        in.readFully(b);
        return new String(b);
    }

    @Override
    public int readInt(Annotation[] fieldAnnotations) throws IOException {
        return in.readInt();
    }

    @Override
    public float readFloat(Annotation[] fieldAnnotations) throws IOException {
        return in.readFloat();
    }

    @Override
    public double readDouble(Annotation[] fieldAnnotations) throws IOException {
        return in.readDouble();
    }

    @Override
    public boolean readBool(Annotation[] fieldAnnotations) throws IOException {
        return in.readBoolean();
    }

    @Override
    public char readChar(Annotation[] fieldAnnotations) throws Exception {
        return in.readChar();
    }

    @Override
    public Object[] readArray(Object[] a, Class<?> type, Annotation[] fieldAnnotations)
            throws Exception {
        var array = (Object[]) Array.newInstance(type, in.readInt());
        for (int i = 0; i < array.length; i++) {
            array[i] = new KineticStreamReader(this).read(type);
        }
        return array;
    }

    @Override
    public void close() throws Exception {}

    @Override
    public byte readByte(Annotation[] fieldAnnotations) throws Exception {
        return in.readByte();
    }

    @Override
    public int[] readIntArray(int[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new int[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readInt(fieldAnnotations);
        }
        return array;
    }

    @Override
    public byte[] readByteArray(byte[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new byte[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readByte(fieldAnnotations);
        }
        return array;
    }

    @Override
    public double[] readDoubleArray(double[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new double[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readDouble(fieldAnnotations);
        }
        return array;
    }

    @Override
    public boolean[] readBooleanArray(boolean[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new boolean[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readBool(fieldAnnotations);
        }
        return array;
    }

    @Override
    public long readLong(Annotation[] fieldAnnotations) throws Exception {
        return in.readLong();
    }

    @Override
    public long[] readLongArray(long[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new long[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readLong(fieldAnnotations);
        }
        return array;
    }

    @Override
    public short readShort(Annotation[] fieldAnnotations) throws Exception {
        return in.readShort();
    }

    @Override
    public short[] readShortArray(short[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new short[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readShort(fieldAnnotations);
        }
        return array;
    }

    @Override
    public String[] readStringArray(String[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new String[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readString(fieldAnnotations);
        }
        return array;
    }

    @Override
    public char[] readCharArray(char[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new char[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readChar(fieldAnnotations);
        }
        return array;
    }

    @Override
    public float[] readFloatArray(float[] a, Annotation[] fieldAnnotations) throws Exception {
        var array = new float[in.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readFloat(fieldAnnotations);
        }
        return array;
    }
}
