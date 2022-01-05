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
package id.kineticstreamer.utils;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import id.xfunction.lang.XRE;

public class KineticUtils {

    /**
     * Creates an object of give type using its default ctor
     */
    public Object createObject(Class<?> type) {
        try {
            var ctor = type.getConstructor();
            if (ctor == null)
                throw new XRE("Type %s has no default ctor",  type);
            return ctor.newInstance();
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for all fields annotated as Streamed in the clazz
     */
    public List<Field> findStreamedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getFields())
                .filter(f -> !Modifier.isTransient(f.getModifiers()))
//                .peek(f -> System.out.println(f.getName()))
                .collect(toList());
    }

}
