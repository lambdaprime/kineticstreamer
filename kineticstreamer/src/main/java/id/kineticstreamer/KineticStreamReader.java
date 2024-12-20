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

import static id.kineticstreamer.KineticStreamTypes.TYPE_NAME_MAP;

import id.kineticstreamer.utils.KineticUtils;
import id.kineticstreamer.utils.ValueSetter;
import id.xfunction.function.ThrowingConsumer;
import id.xfunction.lang.XRE;
import id.xfunction.logging.XLogger;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Stack;

/**
 * Reads Java objects from kinetic streams
 *
 * @author lambdaprime intid@protonmail.com
 */
public class KineticStreamReader {
    private static final XLogger LOGGER = XLogger.getLogger(KineticStreamReader.class);

    private InputKineticStream in;
    private KineticUtils utils = new KineticUtils();
    private KineticStreamController controller = new KineticStreamController();
    private Stack<String> path = new Stack<>();
    private boolean inPlace;

    public KineticStreamReader(InputKineticStream in) {
        this.in = in;
    }

    public KineticStreamReader withController(KineticStreamController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Reads an object from kinetic stream
     *
     * @throws Exception
     */
    public <T> T read(Class<T> type) throws Exception {
        return (T) readInternal(type);
    }

    private Object readInternal(Class<?> type) throws Exception {
        if (type.isArray()) {
            return readArray(null, type.getComponentType(), new Annotation[0]);
        }
        Object[] holder = new Object[1];
        read(type, obj -> holder[0] = obj, new Annotation[0]);
        return holder[0];
    }

    private void read(
            Class<?> type,
            ThrowingConsumer<Object, Exception> setter,
            Annotation[] fieldAnnotations)
            throws Exception {
        LOGGER.fine("Reading object path={0}, type={1}", path, type.getSimpleName());
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null) {
            var obj = utils.createObject(type);
            for (var field : utils.findStreamedFields(type, controller.getFieldsProvider())) {
                readComplexField(obj, field);
            }
            setter.accept(obj);
            return;
        }
        switch (ksPrimitiveType) {
            case STRING:
                setter.accept(in.readString(fieldAnnotations));
                break;
            case INT:
            case INT_WRAPPER:
                setter.accept(in.readInt(fieldAnnotations));
                break;
            case LONG:
            case LONG_WRAPPER:
                setter.accept(in.readLong(fieldAnnotations));
                break;
            case SHORT:
            case SHORT_WRAPPER:
                setter.accept(in.readShort(fieldAnnotations));
                break;
            case FLOAT:
            case FLOAT_WRAPPER:
                setter.accept(in.readFloat(fieldAnnotations));
                break;
            case DOUBLE:
            case DOUBLE_WRAPPER:
                setter.accept(in.readDouble(fieldAnnotations));
                break;
            case BYTE:
            case BYTE_WRAPPER:
                setter.accept(in.readByte(fieldAnnotations));
                break;
            case BOOL:
            case BOOL_WRAPPER:
                setter.accept(in.readBool(fieldAnnotations));
                break;
            case CHAR:
            case CHAR_WRAPPER:
                setter.accept(in.readChar(fieldAnnotations));
                break;
            default:
                throw new XRE("Not supported primitive type %s", type.getName());
        }
    }

    private void readComplexField(Object obj, Field field) throws Exception {
        path.add(obj.getClass().getSimpleName());
        var fieldType = field.getType();
        var objSetter = new ValueSetter(obj, field);
        var fieldObj = field.get(obj);
        if (fieldType.isArray()) {
            LOGGER.fine("Reading object path={0}, type={1}", path, fieldType.getSimpleName());
            Object val = readArray(fieldObj, fieldType.getComponentType(), field.getAnnotations());
            if (!inPlace) objSetter.set(val);
        } else {
            var res = controller.onNextObject(in, fieldObj, fieldType);
            if (res.skip()) {
                if (res.object().isPresent()) objSetter.set(res.object().get());
            } else {
                read(fieldType, objSetter::set, field.getAnnotations());
            }
        }
        path.pop();
    }

    private Object readArray(Object targetArray, Class<?> type, Annotation[] annotations)
            throws Exception {
        inPlace = false;
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null || ksPrimitiveType.isWrapper()) {
            Object val = null;
            var a = (Object[]) targetArray;
            if (a == null) a = new Object[0];
            val = in.readArray(a, type, annotations);
            inPlace = a == val;
            return val;
        }
        Object val = null;
        switch (ksPrimitiveType) {
            case STRING:
                {
                    var a = (String[]) targetArray;
                    if (a == null) a = new String[0];
                    val = in.readStringArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case INT:
                {
                    var a = (int[]) targetArray;
                    if (a == null) a = new int[0];
                    val = in.readIntArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case LONG:
                {
                    var a = (long[]) targetArray;
                    if (a == null) a = new long[0];
                    val = in.readLongArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case SHORT:
                {
                    var a = (short[]) targetArray;
                    if (a == null) a = new short[0];
                    val = in.readShortArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case BYTE:
                {
                    var a = (byte[]) targetArray;
                    if (a == null) a = new byte[0];
                    val = in.readByteArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case DOUBLE:
                {
                    var a = (double[]) targetArray;
                    if (a == null) a = new double[0];
                    val = in.readDoubleArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case BOOL:
                {
                    var a = (boolean[]) targetArray;
                    if (a == null) a = new boolean[0];
                    val = in.readBooleanArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case CHAR:
                {
                    var a = (char[]) targetArray;
                    if (a == null) a = new char[0];
                    val = in.readCharArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            case FLOAT:
                {
                    var a = (float[]) targetArray;
                    if (a == null) a = new float[0];
                    val = in.readFloatArray(a, annotations);
                    inPlace = a == val;
                    break;
                }
            default:
                throw new XRE("Not supported primitive array type %s", type.getName());
        }
        return val;
    }
}
