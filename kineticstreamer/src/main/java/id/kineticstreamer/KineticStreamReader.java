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
import id.xfunction.function.Unchecked;

/**
 * Reads Java objects from kinetic streams
 */
public class KineticStreamReader {

    private InputKineticStream in;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamReader(InputKineticStream in) {
        this.in = in;
    }

    /**
     * Reads an object from kinetic stream
     * @throws Exception
     */
    public Object read(Class<?> type) throws Exception {
        Object[] holder = new Object[1];
        read(type, obj -> holder[0] = obj);
        return holder[0];
    }

    private void read(Class<?> type, ThrowingConsumer<Object, Exception> setter) throws Exception {
        switch (type.getName()) {
        case "java.lang.String": setter.accept(in.readString()); break;
        case "int":
        case "java.lang.Integer": setter.accept(in.readInt()); break;
        case "float":
        case "java.lang.Float": setter.accept(in.readFloat()); break;
        case "double":
        case "java.lang.Double": setter.accept(in.readDouble()); break;
        case "byte":
        case "java.lang.Byte": setter.accept(in.readByte()); break;
        case "boolean":
        case "java.lang.Boolean": setter.accept(in.readBool()); break;
        default: {
            if (type.isArray()) {
                type = type.getComponentType();
                Object a = null;
                if (type == int.class)
                    a = in.readIntArray();
                else if (type == byte.class)
                    a = in.readByteArray();
                else if (type == double.class)
                    a = in.readDoubleArray();
                else if (type == boolean.class)
                    a = in.readBooleanArray();
                else
                    a = in.readArray(type);
                setter.accept(a);
                break;
            } else {
                var obj = utils.createObject(type);
                utils.findStreamedFields(type)
                    .forEach(Unchecked.wrapAccept(field -> read(field.getType(),
                        new ValueSetter(obj, field)::set)));
                setter.accept(obj);
            }
        }
        }
    }

}
