/*
 * Copyright 2022 kineticstreamer project
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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

/** @author lambdaprime intid@protonmail.com */
enum KineticStreamTypes {
    STRING("java.lang.String"),
    INT("int"),
    INT_WRAPPER("java.lang.Integer"),
    LONG("long"),
    LONG_WRAPPER("java.lang.Long"),
    SHORT("short"),
    SHORT_WRAPPER("java.lang.Short"),
    FLOAT("float"),
    FLOAT_WRAPPER("java.lang.Float"),
    DOUBLE("double"),
    DOUBLE_WRAPPER("java.lang.Double"),
    BYTE("byte"),
    BYTE_WRAPPER("java.lang.Byte"),
    BOOL("boolean"),
    BOOL_WRAPPER("java.lang.Boolean");

    public static final Map<String, KineticStreamTypes> TYPE_NAME_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(k -> k.type, v -> v));
    public static final EnumSet<KineticStreamTypes> WRAPPERS =
            EnumSet.of(
                    INT_WRAPPER,
                    LONG_WRAPPER,
                    SHORT_WRAPPER,
                    FLOAT_WRAPPER,
                    DOUBLE_WRAPPER,
                    BYTE_WRAPPER,
                    BOOL_WRAPPER);
    private String type;

    KineticStreamTypes(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }

    boolean isWrapper() {
        return WRAPPERS.contains(this);
    }
}
