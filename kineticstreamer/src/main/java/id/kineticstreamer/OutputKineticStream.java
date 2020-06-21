/*
 * Copyright 2020 lambdaprime
 */

package id.kineticstreamer;

public interface OutputKineticStream {

    void writeString(String str) throws Exception;
    void writeInt(Integer i) throws Exception;
    void writeDouble(Double f) throws Exception;
    void writeFloat(Float f) throws Exception;
    void writeBoolean(Boolean fieldValue) throws Exception;
    void writeArray(Object[] array) throws Exception;

}
