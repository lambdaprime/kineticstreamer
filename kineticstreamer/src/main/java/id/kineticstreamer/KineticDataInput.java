/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.io.IOException;

public interface KineticDataInput {

    int readLen() throws IOException;
    String readString() throws IOException;
    int readInt() throws IOException;
    float readFloat() throws IOException;
    double readDouble() throws IOException;

}
