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

import id.kineticstreamer.utils.KineticUtils;
import id.kineticstreamer.utils.ValueSetter;
import id.xfunction.function.ThrowingConsumer;
import id.xfunction.logging.XLogger;

/**
 * Reads Java objects from kinetic streams
 */
public class KineticStreamReader {
    private static final XLogger LOGGER = XLogger.getLogger(KineticStreamReader.class);

    private InputKineticStream in;
    private KineticUtils utils = new KineticUtils();
    private KineticStreamReaderController controller = new KineticStreamReaderController();
    private boolean inPlace;

    public KineticStreamReader(InputKineticStream in) {
        this.in = in;
    }

    public KineticStreamReader withController(KineticStreamReaderController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Reads an object from kinetic stream
     * @throws Exception
     */
    public <T> T read(Class<T> type) throws Exception {
        return (T) readInternal(type);
    }

    private Object readInternal(Class<?> type) throws Exception {
        if (type.isArray()) {
            return readArray(null, type.getComponentType());
        }
        Object[] holder = new Object[1];
        read(type, obj -> holder[0] = obj);
        return holder[0];
    }
    
    private void read(Class<?> type, ThrowingConsumer<Object, Exception> setter) throws Exception {
        LOGGER.fine("Reading object type {0}", type.getSimpleName());
        switch (type.getName()) {
        case "java.lang.String": setter.accept(in.readString()); break;
        case "int":
        case "java.lang.Integer": setter.accept(in.readInt()); break;
        case "long":
        case "java.lang.Long": setter.accept(in.readLong()); break;
        case "short":
        case "java.lang.Short": setter.accept(in.readShort()); break;
        case "float":
        case "java.lang.Float": setter.accept(in.readFloat()); break;
        case "double":
        case "java.lang.Double": setter.accept(in.readDouble()); break;
        case "byte":
        case "java.lang.Byte": setter.accept(in.readByte()); break;
        case "boolean":
        case "java.lang.Boolean": setter.accept(in.readBool()); break;
        default: {
            var obj = utils.createObject(type);
            utils.findStreamedFields(type).forEach(field -> {
                try {
                    var fieldType = field.getType();
                    var objSetter = new ValueSetter(obj, field);
                    if (!fieldType.isArray()) {
                        var res = controller.onNextObject(obj, fieldType);
                        if (res.skip) {
                            if (res.object.isPresent())
                                objSetter.set(res.object.get());
                        } else {
                            read(fieldType, objSetter::set);
                        }
                    } else {
                        Object val = readArray(field.get(obj), fieldType.getComponentType());
                        if (!inPlace) objSetter.set(val);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            setter.accept(obj);
        }
        }
    }

    private Object readArray(Object targetArray, Class<?> type) throws Exception {
        Object val = null;
        inPlace = false;
        if (type == int.class) {
            var a = (int[])targetArray;
            if (a == null) a = new int[0];
            val = in.readIntArray(a);
            inPlace = a == val;
        } else if (type == long.class) {
            var a = (long[])targetArray;
            if (a == null) a = new long[0];
            val = in.readLongArray(a);
            inPlace = a == val;
        } else if (type == short.class) {
            var a = (short[])targetArray;
            if (a == null) a = new short[0];
            val = in.readShortArray(a);
            inPlace = a == val;
        } else if (type == byte.class) {
            var a = (byte[])targetArray;
            if (a == null) a = new byte[0];
            val = in.readByteArray(a);
            inPlace = a == val;
        } else if (type == double.class) {
            var a = (double[])targetArray;
            if (a == null) a = new double[0];
            val = in.readDoubleArray(a);
            inPlace = a == val;
        } else if (type == boolean.class) {
            var a = (boolean[])targetArray;
            if (a == null) a = new boolean[0];
            val = in.readBooleanArray(a);
            inPlace = a == val;
        } else {
            var a = (Object[])targetArray;
            if (a == null) a = new Object[0];
            val = in.readArray(a, type);
            inPlace = a == val;
        }
        return val;
    }
}
