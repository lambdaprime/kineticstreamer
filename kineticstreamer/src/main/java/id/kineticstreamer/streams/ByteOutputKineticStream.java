/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer.streams;

import java.io.DataOutput;
import java.io.IOException;

import id.kineticstreamer.OutputKineticStream;

public class ByteOutputKineticStream implements OutputKineticStream {

    private DataOutput out;

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

}
