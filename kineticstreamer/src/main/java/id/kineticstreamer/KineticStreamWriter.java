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

import static id.kineticstreamer.KineticStreamTypes.TYPE_NAME_MAP;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.lang.XRE;
import id.xfunction.logging.XLogger;

/**
 * Writes Java objects into kinetic streams
 */
public class KineticStreamWriter {
    private static final XLogger LOGGER = XLogger.getLogger(KineticStreamWriter.class);

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();
    private KineticStreamWriterController controller = new KineticStreamWriterController();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public KineticStreamWriter withController(KineticStreamWriterController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Writes an object into kinetic stream
     * @throws Exception
     */
    public void write(Object obj) throws Exception {
        if (obj == null) return;
        LOGGER.fine("Writing object type {0}", obj.getClass().getSimpleName());
        var type = obj.getClass();
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null) {
            if (type.isArray()) {
                writeArray(obj, type.getComponentType());
            } else {
                for (var field: utils.findStreamedFields(type)) {
                    var fieldObj = field.get(obj);
                    if (controller.onNextObject(out, fieldObj).skip) continue;
                    write(fieldObj);
                }
            }
            return;
        }
        switch (ksPrimitiveType) {
        case STRING: out.writeString((String)obj); break;
        case INT:
        case INT_WRAPPER: out.writeInt((Integer)obj); break;
        case LONG:
        case LONG_WRAPPER: out.writeLong((Long)obj); break;
        case SHORT:
        case SHORT_WRAPPER: out.writeShort((Short)obj); break;
        case FLOAT:
        case FLOAT_WRAPPER: out.writeFloat((Float)obj); break;
        case DOUBLE:
        case DOUBLE_WRAPPER: out.writeDouble((Double)obj); break;
        case BYTE:
        case BYTE_WRAPPER: out.writeByte((Byte)obj); break;
        case BOOL:
        case BOOL_WRAPPER: out.writeBoolean((Boolean)obj); break;
        default: throw new XRE("Not supported primitive type %s", type.getName());
        }
    }

    private void writeArray(Object obj, Class<?> type) throws Exception {
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null || ksPrimitiveType.isWrapper()) {
            out.writeArray((Object[]) obj);
            return;
        }
        switch (ksPrimitiveType) {
        case INT: out.writeIntArray((int[]) obj); break;
        case BYTE: out.writeByteArray((byte[]) obj); break;
        case SHORT: out.writeShortArray((short[]) obj); break;
        case DOUBLE: out.writeDoubleArray((double[]) obj); break;
        case BOOL: out.writeBooleanArray((boolean[]) obj); break;
        default: throw new XRE("Not supported primitive array type %s", type.getName());
        }
    }
}
