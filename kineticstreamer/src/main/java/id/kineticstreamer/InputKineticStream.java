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
package id.kineticstreamer;

import java.lang.annotation.Annotation;

/**
 * Interface which needs to be implemented to support deserialization of new types of kinetic
 * streams.
 *
 * <p>All of them read values from the stream which were previously written there by {@link
 * OutputKineticStream}.
 *
 * <p>Methods for reading arrays are called with the value from the field of the object which is
 * being deserialized. This helps to populate fixed size array fields. Users can populate such
 * arrays directly and then return reference back so that <b>kineticstreamer</b> don't have to
 * allocate new arrays for them.
 *
 * <p>Each method additionally accepts all field annotations associated with the field, value for
 * which is currently read.
 *
 * @author lambdaprime intid@protonmail.com
 */
public interface InputKineticStream extends AutoCloseable {

    String readString(Annotation[] fieldAnnotations) throws Exception;

    int readInt(Annotation[] fieldAnnotations) throws Exception;

    float readFloat(Annotation[] fieldAnnotations) throws Exception;

    double readDouble(Annotation[] fieldAnnotations) throws Exception;

    boolean readBool(Annotation[] fieldAnnotations) throws Exception;

    byte readByte(Annotation[] fieldAnnotations) throws Exception;

    char readChar(Annotation[] fieldAnnotations) throws Exception;

    Object[] readArray(Object[] a, Class<?> type, Annotation[] fieldAnnotations) throws Exception;

    int[] readIntArray(int[] a, Annotation[] fieldAnnotations) throws Exception;

    byte[] readByteArray(byte[] a, Annotation[] fieldAnnotations) throws Exception;

    double[] readDoubleArray(double[] a, Annotation[] fieldAnnotations) throws Exception;

    boolean[] readBooleanArray(boolean[] a, Annotation[] fieldAnnotations) throws Exception;

    String[] readStringArray(String[] a, Annotation[] fieldAnnotations) throws Exception;

    long readLong(Annotation[] fieldAnnotations) throws Exception;

    long[] readLongArray(long[] a, Annotation[] fieldAnnotations) throws Exception;

    short readShort(Annotation[] fieldAnnotations) throws Exception;

    short[] readShortArray(short[] a, Annotation[] fieldAnnotations) throws Exception;

    char[] readCharArray(char[] a, Annotation[] fieldAnnotations) throws Exception;

    float[] readFloatArray(float[] a, Annotation[] fieldAnnotations) throws Exception;
}
