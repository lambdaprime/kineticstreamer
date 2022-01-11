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

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import id.kineticstreamer.InputKineticStream;
import id.kineticstreamer.KineticStreamReader;

/**
 * Kinetic stream implementation for deserialization of Java objects
 * from stream of CSV lines.
 */
public class CsvInputKineticStream implements InputKineticStream {

    private BufferedReader in;
    // list of tokens of currently read line
    private List<String> tokens = List.of();

    /**
     * Create CSV kinetic input stream
     * @param lines lines reader from CSV file
     */
    public CsvInputKineticStream(BufferedReader lines) {
        this.in = lines;
    }

    @Override
    public String readString() throws IOException {
        advance();
        return tokens.remove(0);
    }

    private void advance() throws IOException {
        if (!tokens.isEmpty()) return;
        String line = in.readLine();
        if (line == null) throw new NoSuchElementException();
        tokens = Pattern.compile(";").splitAsStream(line)
                .collect(toList());
    }

    @Override
    public int readInt() throws IOException {
        advance();
        return Integer.parseInt(tokens.remove(0));
    }

    @Override
    public float readFloat() throws IOException {
        advance();
        return Float.parseFloat(tokens.remove(0));
    }

    @Override
    public double readDouble() throws IOException {
        advance();
        return Double.parseDouble(tokens.remove(0));
    }

    @Override
    public boolean readBool() throws IOException {
        advance();
        return Boolean.parseBoolean(tokens.remove(0));
    }

    @Override
    public Object[] readArray(Object[] a, Class<?> type) throws Exception {
        List<Object> l = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            l.add(new KineticStreamReader(new CsvInputKineticStream(
                new BufferedReader(new StringReader(line)))).read(type));
        }
        return l.toArray((Object[]) Array.newInstance(type, 0));
    }

    @Override
    public void close() throws Exception {
        in.close();
    }

    @Override
    public byte readByte() throws Exception {
        advance();
        return Byte.parseByte(tokens.remove(0));
    }

    @Override
    public int[] readIntArray(int[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public byte[] readByteArray(byte[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public double[] readDoubleArray(double[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean[] readBooleanArray(boolean[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public long readLong() throws Exception {
        advance();
        return Long.parseLong(tokens.remove(0));
    }

    @Override
    public long[] readLongArray(long[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public short readShort() throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public short[] readShortArray(short[] a) throws Exception {
        throw new RuntimeException("Not supported");
    }
}
