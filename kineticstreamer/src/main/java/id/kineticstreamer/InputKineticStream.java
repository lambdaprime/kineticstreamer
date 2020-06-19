/*
 * Copyright 2020 lambdaprime
 */
package id.kineticstreamer;

import java.io.IOException;

public interface InputKineticStream {

    String readString() throws IOException;
    int readInt() throws IOException;
    float readFloat() throws IOException;
    double readDouble() throws IOException;
    boolean readBool() throws IOException;

}