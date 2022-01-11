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

import java.util.List;

/**
 * <p>Interface which needs to be implemented to support serialization of
 * new types of kinetic streams.</p>
 * 
 * <p>All of its methods write values of certain data types into stream
 * which later can be read back by {@link InputKineticStream}.</p>
 */
public interface OutputKineticStream extends AutoCloseable {

    void writeString(String str) throws Exception;
    void writeInt(Integer i) throws Exception;
    void writeDouble(Double f) throws Exception;
    void writeFloat(Float f) throws Exception;
    void writeBoolean(Boolean fieldValue) throws Exception;
    void writeByte(Byte fieldValue) throws Exception;
    void writeArray(Object[] array) throws Exception;
    void writeIntArray(int[] array) throws Exception;
    void writeByteArray(byte[] array) throws Exception;
    void writeDoubleArray(double[] array) throws Exception;
    void writeBooleanArray(boolean[] array) throws Exception;
    void writeLong(Long l) throws Exception;
    void writeShort(Short s) throws Exception;
    void writeShortArray(short[] a) throws Exception;
    
    /**
     * Write list to the stream
     * 
     * @param list list to write
     * @param genericType parameterized type of the list. For example String in case List&lt;String>
     */
    void writeList(List<?> list, Class<?> genericType) throws Exception;
}
