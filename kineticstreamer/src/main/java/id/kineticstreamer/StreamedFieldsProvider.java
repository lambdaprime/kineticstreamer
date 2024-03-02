/*
 * Copyright 2024 kineticstreamer project
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

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author lambdaprime intid@protonmail.com
 */
@FunctionalInterface
public interface StreamedFieldsProvider {

    /**
     * List of all "declared" streamed fields for a given class ("declared" fields does not include
     * fields in parent classes).
     *
     * <p>Fields will be (de)serialized in same order as they present inside returned list.
     *
     * <p>Note: it is not recommended to rely on the order of fields as they are returned by {@link
     * Class#getDeclaredFields()}. Reason is because this method does not guarantee any order which
     * would affect the (de)serialization process (for example Android 14 JVM returns fields in a
     * different order than OpenJDK 17).
     */
    List<Field> getDeclaredStreamedFields(Class<?> clazz);
}
