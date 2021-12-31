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
/*
 * Authors:
 * - lambdaprime <intid@protonmail.com>
 */
package id.kineticstreamer;

/**
 * <p>Interface which needs to be implemented to support deserialization of
 * new types of kinetic streams.</p>
 * 
 * <p>All of them read values from the stream which were previously
 * written there by {@link OutputKineticStream}.</p>
 * 
 * <p>Methods for reading arrays are called with the value from the field of the
 * object which is being deserialized. This helps to populate fixed size array
 * fields. Users can populate such arrays directly and then return reference back
 * so that <b>kineticstreamer</b> don't have to allocate new arrays for them.
 */
public interface InputKineticStream extends AutoCloseable {

    String readString() throws Exception;
    int readInt() throws Exception;
    float readFloat() throws Exception;
    double readDouble() throws Exception;
    boolean readBool() throws Exception;
    byte readByte() throws Exception;
    Object[] readArray(Object[] a, Class<?> type) throws Exception;
    int[] readIntArray(int[] a) throws Exception;
    byte[] readByteArray(byte[] a) throws Exception;
    double[] readDoubleArray(double[] a) throws Exception;
    boolean[] readBooleanArray(boolean[] a) throws Exception;
    long readLong() throws Exception;
    long[] readLongArray(long[] a) throws Exception;
    short readShort() throws Exception;
    short[] readShortArray(short[] a) throws Exception;
}
