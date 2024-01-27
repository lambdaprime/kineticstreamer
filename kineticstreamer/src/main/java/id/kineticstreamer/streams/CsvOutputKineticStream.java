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
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Kinetic stream implementation for serialization of Java objects into CSV files.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class CsvOutputKineticStream implements OutputKineticStream {

    private BufferedWriter out;

    public CsvOutputKineticStream(BufferedWriter out) {
        this.out = out;
    }

    @Override
    public void writeString(String str, Annotation[] fieldAnnotations) throws IOException {
        out.write(str);
        out.write(';');
    }

    @Override
    public void writeInt(Integer i, Annotation[] fieldAnnotations) throws IOException {
        out.write(i.toString());
        out.write(';');
    }

    @Override
    public void writeDouble(Double f, Annotation[] fieldAnnotations) throws IOException {
        out.write(f.toString());
        out.write(';');
    }

    @Override
    public void writeFloat(Float f, Annotation[] fieldAnnotations) throws IOException {
        out.write(f.toString());
        out.write(';');
    }

    @Override
    public void writeBoolean(Boolean b, Annotation[] fieldAnnotations) throws IOException {
        out.write(b.toString());
        out.write(';');
    }

    @Override
    public void writeArray(Object[] array, Annotation[] fieldAnnotations) throws Exception {
        for (int i = 0; i < array.length; i++) {
            new KineticStreamWriter(new CsvOutputKineticStream(out)).write(array[i]);
            out.write('\n');
        }
    }

    @Override
    public void close() throws Exception {
        out.close();
    }

    @Override
    public void writeByte(Byte b, Annotation[] fieldAnnotations) throws Exception {
        out.write(b.toString());
        out.write(';');
    }

    @Override
    public void writeIntArray(int[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeByteArray(byte[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeDoubleArray(double[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeBooleanArray(boolean[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeLong(Long l, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeShort(Short s, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeShortArray(short[] a, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeStringArray(String[] obj, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeChar(Character ch, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeCharArray(char[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeFloatArray(float[] array, Annotation[] fieldAnnotations) throws Exception {
        throw new RuntimeException("Not supported");
    }
}
