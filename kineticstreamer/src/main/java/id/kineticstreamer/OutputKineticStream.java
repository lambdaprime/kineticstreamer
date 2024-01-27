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
 * Interface which needs to be implemented to support serialization of new types of kinetic streams.
 *
 * <p>All of its methods write values of certain data types into the stream which later can be read
 * back by {@link InputKineticStream}.
 *
 * <p>Each method additionally accepts all field annotations associated with the field which value
 * is written.
 *
 * @author lambdaprime intid@protonmail.com
 */
public interface OutputKineticStream extends AutoCloseable {

    void writeString(String fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeInt(Integer fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeDouble(Double fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeFloat(Float fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeBoolean(Boolean fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeByte(Byte fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeChar(Character fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeArray(Object[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeIntArray(int[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeByteArray(byte[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeDoubleArray(double[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeBooleanArray(boolean[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeLong(Long fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeShort(Short fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeShortArray(short[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeStringArray(String[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeCharArray(char[] fieldValue, Annotation[] fieldAnnotations) throws Exception;

    void writeFloatArray(float[] fieldValue, Annotation[] fieldAnnotations) throws Exception;
}
