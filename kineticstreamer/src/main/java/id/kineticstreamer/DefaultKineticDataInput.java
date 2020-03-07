/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.io.DataInput;
import java.io.IOException;

public class DefaultKineticDataInput implements KineticDataInput {

    private DataInput in;

    public DefaultKineticDataInput(DataInput in) {
        this.in = in;
    }

    @Override
    public int readeLen() throws IOException {
        return Integer.reverseBytes(in.readInt());
    }

    @Override
    public String readString() throws IOException {
        int len = readeLen();
        byte[] b = new byte[len];
        in.readFully(b);
        return new String(b);
    }

    @Override
    public int readInt() throws IOException {
        return in.readInt();
    }

}
