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
    public void writeString(String str) throws IOException {
        out.write(str);
        out.write(';');
    }

    @Override
    public void writeInt(Integer i) throws IOException {
        out.write(i.toString());
        out.write(';');
    }

    @Override
    public void writeDouble(Double f) throws IOException {
        out.write(f.toString());
        out.write(';');
    }

    @Override
    public void writeFloat(Float f) throws IOException {
        out.write(f.toString());
        out.write(';');
    }

    @Override
    public void writeBoolean(Boolean b) throws IOException {
        out.write(b.toString());
        out.write(';');
    }

    @Override
    public void writeArray(Object[] array) throws Exception {
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
    public void writeByte(Byte b) throws Exception {
        out.write(b.toString());
        out.write(';');
    }

    @Override
    public void writeIntArray(int[] array) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeByteArray(byte[] array) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeDoubleArray(double[] array) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeBooleanArray(boolean[] array) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeLong(Long l) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeShort(Short s) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeShortArray(short[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void writeStringArray(String[] obj) throws Exception {
        throw new RuntimeException("Not supported");
    }
}
