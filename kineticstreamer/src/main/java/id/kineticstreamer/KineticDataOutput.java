/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

import java.io.IOException;

public interface KineticDataOutput {

    void writeLen(int len) throws IOException;
    void writeString(String str) throws IOException;
    void writeInt(Integer i) throws IOException;
    void writeDouble(Double f) throws IOException;
    void writeFloat(Float f) throws IOException;
    void writeBoolean(Boolean fieldValue) throws IOException;

}
