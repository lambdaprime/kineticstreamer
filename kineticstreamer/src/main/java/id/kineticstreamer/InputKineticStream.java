/*
 * Copyright 2020 lambdaprime
 */
package id.kineticstreamer;

public interface InputKineticStream {

    String readString() throws Exception;
    int readInt() throws Exception;
    float readFloat() throws Exception;
    double readDouble() throws Exception;
    boolean readBool() throws Exception;
    Object[] readArray(Class<?> type) throws Exception;

}
