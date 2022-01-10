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

import static id.kineticstreamer.KineticstreamerPrimitiveTypes.TYPE_NAME_MAP;

import id.kineticstreamer.utils.KineticUtils;
import id.xfunction.function.Unchecked;
import id.xfunction.lang.XRE;
import id.xfunction.logging.XLogger;

/**
 * Writes Java objects into kinetic streams
 */
public class KineticStreamWriter {
    private static final XLogger LOGGER = XLogger.getLogger(KineticStreamWriter.class);

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    /**
     * Writes an object into kinetic stream
     * @throws Exception
     */
    public void write(Object b) throws Exception {
        if (b == null) return;
        LOGGER.fine("Writing object type {0}", b.getClass().getSimpleName());
        var type = b.getClass();
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null) {
            if (type.isArray()) {
                writeArray(b, type.getComponentType());
            } else {
                utils.findStreamedFields(type).stream()
                    .map(f -> Unchecked.get(() -> f.get(b)))
                    .forEach(Unchecked.wrapAccept(this::write));
            }
            return;
        }
        switch (ksPrimitiveType) {
        case STRING: out.writeString((String)b); break;
        case INT:
        case INT_WRAPPER: out.writeInt((Integer)b); break;
        case LONG:
        case LONG_WRAPPER: out.writeLong((Long)b); break;
        case SHORT:
        case SHORT_WRAPPER: out.writeShort((Short)b); break;
        case FLOAT:
        case FLOAT_WRAPPER: out.writeFloat((Float)b); break;
        case DOUBLE:
        case DOUBLE_WRAPPER: out.writeDouble((Double)b); break;
        case BYTE:
        case BYTE_WRAPPER: out.writeByte((Byte)b); break;
        case BOOL:
        case BOOL_WRAPPER: out.writeBoolean((Boolean)b); break;
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
