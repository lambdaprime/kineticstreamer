/**
 * Copyright 2020 lambdaprime
 * 
 * Email: id.blackmesa@gmail.com 
 * Website: https://github.com/lambdaprime
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

import id.xfunction.function.Unchecked;

public class KineticStreamWriter {

    private OutputKineticStream out;
    private KineticUtils utils = new KineticUtils();

    public KineticStreamWriter(OutputKineticStream out) {
        this.out = out;
    }

    public void write(Object b) throws Exception {
        if (b == null) return;
        var type = b.getClass();
        switch (type.getName()) {
        case "java.lang.String": out.writeString((String)b); break;
        case "int":
        case "java.lang.Integer": out.writeInt((Integer)b); break;
        case "float":
        case "java.lang.Float": out.writeFloat((Float)b); break;
        case "double":
        case "java.lang.Double": out.writeDouble((Double)b); break;
        case "boolean":
        case "java.lang.Boolean": out.writeBoolean((Boolean)b); break;
        default: {
            if (type.isArray()) {
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
