/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer.streams;

import java.io.DataInput;
import java.io.IOException;

import id.kineticstreamer.InputKineticStream;

public class ByteInputKineticStream implements InputKineticStream {

    private DataInput in;

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

}
