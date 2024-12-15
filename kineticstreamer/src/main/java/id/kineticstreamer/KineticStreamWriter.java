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
import id.xfunction.Preconditions;
import id.xfunction.lang.XRE;
import id.xfunction.logging.XLogger;
import java.lang.annotation.Annotation;
import java.util.Stack;

/**
 * Writes Java objects into kinetic streams
 *
 * @author lambdaprime intid@protonmail.com
 */
public class KineticStreamWriter {
    private static final XLogger LOGGER = XLogger.getLogger(KineticStreamWriter.class);

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();
    private Stack<String> path = new Stack<>();
    private KineticStreamController controller = new KineticStreamController();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public KineticStreamWriter withController(KineticStreamController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Writes an object into kinetic stream
     *
     * @throws Exception
     */
    public void write(Object obj, Annotation... annotations) throws Exception {
        Preconditions.notNull(obj, "Serialization of null values is not supported");
        var className = obj.getClass().getSimpleName();
        LOGGER.fine("Writing object path={0}, type={1}", path, className);
        var type = obj.getClass();
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null) {
            if (type.isArray()) {
                writeArray(obj, type.getComponentType(), annotations);
            } else {
                path.add(className);
                for (var field : utils.findStreamedFields(type, controller.getFieldsProvider())) {
                    var fieldObj = field.get(obj);
                    if (controller.onNextObject(out, fieldObj).skip) continue;
                    write(fieldObj, field.getAnnotations());
                }
                path.pop();
            }
            return;
        }
        switch (ksPrimitiveType) {
            case STRING:
                out.writeString((String) obj, annotations);
                break;
            case INT:
            case INT_WRAPPER:
                out.writeInt((Integer) obj, annotations);
                break;
            case LONG:
            case LONG_WRAPPER:
                out.writeLong((Long) obj, annotations);
                break;
            case SHORT:
            case SHORT_WRAPPER:
                out.writeShort((Short) obj, annotations);
                break;
            case FLOAT:
            case FLOAT_WRAPPER:
                out.writeFloat((Float) obj, annotations);
                break;
            case DOUBLE:
            case DOUBLE_WRAPPER:
                out.writeDouble((Double) obj, annotations);
                break;
            case BYTE:
            case BYTE_WRAPPER:
                out.writeByte((Byte) obj, annotations);
                break;
            case BOOL:
            case BOOL_WRAPPER:
                out.writeBoolean((Boolean) obj, annotations);
                break;
            case CHAR:
            case CHAR_WRAPPER:
                out.writeChar((Character) obj, annotations);
                break;
            default:
                throw new XRE("Not supported primitive type %s", type.getName());
        }
    }

    private void writeArray(Object obj, Class<?> type, Annotation[] annotations) throws Exception {
        var ksPrimitiveType = TYPE_NAME_MAP.get(type.getName());
        if (ksPrimitiveType == null || ksPrimitiveType.isWrapper()) {
            out.writeArray((Object[]) obj, annotations);
            return;
        }
        switch (ksPrimitiveType) {
            case STRING:
                out.writeStringArray((String[]) obj, annotations);
                break;
            case INT:
                out.writeIntArray((int[]) obj, annotations);
                break;
            case BYTE:
                out.writeByteArray((byte[]) obj, annotations);
                break;
            case SHORT:
                out.writeShortArray((short[]) obj, annotations);
                break;
            case DOUBLE:
                out.writeDoubleArray((double[]) obj, annotations);
                break;
            case BOOL:
                out.writeBooleanArray((boolean[]) obj, annotations);
                break;
            case CHAR:
                out.writeCharArray((char[]) obj, annotations);
                break;
            case FLOAT:
                out.writeFloatArray((float[]) obj, annotations);
                break;
            default:
                throw new XRE("Not supported primitive array type %s", type.getName());
        }
    }
}
