/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.io.DataOutput;
import java.io.IOException;

public class DefaultKineticDataOutput implements KineticDataOutput {

    private DataOutput out;

    public DefaultKineticDataOutput(DataOutput out) {
        this.out = out;
    }

    @Override
    public void writeLen(int len) throws IOException {
        out.writeInt(Integer.reverseBytes(len));
    }

    @Override
    public void writeString(String str) throws IOException {
        writeLen(str.length());
        out.write(str.getBytes());
    }

    @Override
    public void writeInt(Integer i) throws IOException {
        out.writeInt(i);
    }

}
