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
package id.kineticstreamer.utils;

import id.kineticstreamer.StreamedFieldsProvider;
import id.xfunction.lang.XRE;
import id.xfunction.logging.XLogger;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class KineticUtils {
    private static final XLogger LOGGER = XLogger.getLogger(KineticUtils.class);
    private static final Map<Class<?>, List<Field>> cache = new ConcurrentHashMap<>();

    /** Creates an object of give type using its default ctor */
    public Object createObject(Class<?> type) {
        try {
            var ctor = type.getConstructor();
            if (ctor == null) throw new XRE("Type %s has no default ctor", type);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Searches for all streamed fields in the clazz and not only declared. */
    public List<Field> findStreamedFields(
            Class<?> clazz, StreamedFieldsProvider streamedFieldsProvider) {
        if (clazz == Object.class) return List.of();
        if (clazz.isInterface()) return List.of();
        var out = cache.get(clazz);
        if (out == null) {
            LOGGER.fine("Finding streamed fields for class {0}", clazz.getSimpleName());
            out =
                    new ArrayList<>(
                            findStreamedFields(clazz.getSuperclass(), streamedFieldsProvider));
            streamedFieldsProvider.getDeclaredStreamedFields(clazz).forEach(out::add);
            out = List.copyOf(out);
            cache.put(clazz, out);
        } else {
            LOGGER.fine(
                    "Streamed fields for class {0} are available in the cache, returning them",
                    clazz.getSimpleName());
        }
        return out;
    }
}
