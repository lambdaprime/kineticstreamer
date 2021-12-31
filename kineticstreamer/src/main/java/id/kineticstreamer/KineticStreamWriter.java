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
import id.xfunction.function.Unchecked;
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
        switch (type.getName()) {
        case "java.lang.String": out.writeString((String)b); break;
        case "int":
        case "java.lang.Integer": out.writeInt((Integer)b); break;
        case "long":
        case "java.lang.Long": out.writeLong((Long)b); break;
        case "short":
        case "java.lang.Short": out.writeShort((Short)b); break;
        case "float":
        case "java.lang.Float": out.writeFloat((Float)b); break;
        case "double":
        case "java.lang.Double": out.writeDouble((Double)b); break;
        case "byte":
        case "java.lang.Byte": out.writeByte((Byte)b); break;
        case "boolean":
        case "java.lang.Boolean": out.writeBoolean((Boolean)b); break;
        default: {
            if (type.isArray()) {
                if (type.getComponentType() == int.class)
                    out.writeIntArray((int[]) b);
                else if (type.getComponentType() == byte.class)
                    out.writeByteArray((byte[]) b);
                else if (type.getComponentType() == short.class)
                    out.writeShortArray((short[]) b);
                else if (type.getComponentType() == double.class)
                    out.writeDoubleArray((double[]) b);
                else if (type.getComponentType() == boolean.class)
                    out.writeBooleanArray((boolean[]) b);
                else 
                    out.writeArray((Object[]) b);
                break;
            } else {
                utils.findStreamedFields(type).stream()
                    .map(f -> Unchecked.get(() -> f.get(b)))
                    .forEach(Unchecked.wrapAccept(this::write));
            }
        }
        }
    }
}
