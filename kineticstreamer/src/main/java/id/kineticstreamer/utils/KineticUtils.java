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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import id.xfunction.lang.XRE;

public class KineticUtils {
    private static final Map<Class<?>, List<Field>> cache = new ConcurrentHashMap<>();

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
     * Searches for all fields annotated as Streamed in the clazz.
     */
    // TODO this method must return fields in same order as they declared in the class.
    // Unfortunately Class.getDeclaredFields() may return them in any order which can
    // cause issues on certain JVMs
    public List<Field> findStreamedFields(Class<?> clazz) {
        if (clazz == Object.class) return List.of();
        if (clazz.isInterface()) return List.of();
        if (cache.containsKey(clazz)) return cache.get(clazz);
        List<Field> out = new ArrayList<>(findStreamedFields(clazz.getSuperclass()));
        Arrays.stream(clazz.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()))
            .filter(f -> !Modifier.isTransient(f.getModifiers()))
            .filter(f -> !Modifier.isFinal(f.getModifiers()))
//            .peek(f -> System.out.println(f.getName()))
            .forEach(out::add);
        out = List.copyOf(out);
        cache.put(clazz, out);
        return out;
    }

}
