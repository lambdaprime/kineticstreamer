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

import id.kineticstreamer.KineticStreamWriter;
import id.kineticstreamer.OutputKineticStream;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Kinetic stream implementation for serialization of Java objects into sequence of bytes.
 *
 * <p>Any array 'A' serialized as follows: writeInt(A.length) | KineticStreamWriter::write(a[0]) |
 * ... | KineticStreamWriter::write(a[N])
 *
 * <p>Where write call for each item will use KineticStreamWriter on same kinetic stream object (ie.
 * 'this').
 *
 * @author lambdaprime intid@protonmail.com
 */
public class ByteOutputKineticStream implements OutputKineticStream {

    private DataOutput out;

    /** Creates byte kinetic stream and attaches it to 'out' */
    public ByteOutputKineticStream(DataOutput out) {
        this.out = out;
    }

    @Override
    public void writeString(String str, Annotation[] fieldAnnotations) throws IOException {
        out.writeInt(str.length());
        out.write(str.getBytes());
    }

    @Override
    public void writeInt(Integer i, Annotation[] fieldAnnotations) throws IOException {
        out.writeInt(i);
    }

    @Override
    public void writeDouble(Double f, Annotation[] fieldAnnotations) throws IOException {
        out.writeDouble(f);
    }

    @Override
    public void writeFloat(Float f, Annotation[] fieldAnnotations) throws IOException {
        out.writeFloat(f);
    }

    @Override
    public void writeBoolean(Boolean b, Annotation[] fieldAnnotations) throws IOException {
        out.writeBoolean(b);
    }

    @Override
    public void writeArray(Object[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            new KineticStreamWriter(this).write(item);
        }
    }

    @Override
    public void close() throws Exception {}

    @Override
    public void writeByte(Byte b, Annotation[] fieldAnnotations) throws Exception {
        out.writeByte(b);
    }

    @Override
    public void writeChar(Character ch, Annotation[] fieldAnnotations) throws Exception {
        out.writeChar(ch);
    }

    @Override
    public void writeIntArray(int[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeInt(item, fieldAnnotations);
        }
    }

    @Override
    public void writeByteArray(byte[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeByte(item, fieldAnnotations);
        }
    }

    @Override
    public void writeDoubleArray(double[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeDouble(item, fieldAnnotations);
        }
    }

    @Override
    public void writeBooleanArray(boolean[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeBoolean(item, fieldAnnotations);
        }
    }

    @Override
    public void writeLong(Long l, Annotation[] fieldAnnotations) throws Exception {
        out.writeLong(l);
    }

    @Override
    public void writeShort(Short s, Annotation[] fieldAnnotations) throws Exception {
        out.writeShort(s);
    }

    @Override
    public void writeShortArray(short[] a, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(a.length);
        for (var item : a) {
            writeShort(item, fieldAnnotations);
        }
    }

    @Override
    public void writeStringArray(String[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeString(item, fieldAnnotations);
        }
    }

    @Override
    public void writeCharArray(char[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeChar(item, fieldAnnotations);
        }
    }

    @Override
    public void writeFloatArray(float[] array, Annotation[] fieldAnnotations) throws Exception {
        out.writeInt(array.length);
        for (var item : array) {
            writeFloat(item, fieldAnnotations);
        }
    }
}
