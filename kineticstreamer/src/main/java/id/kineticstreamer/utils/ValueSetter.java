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

public class ValueSetter {

    private Object obj;
    private Field field;

    public ValueSetter(Object obj, Field field) {
        this.obj = obj;
        this.field = field;
    }

    public void set(Object value) throws Exception {
        Class<?> type = field.getType();
        field.set(obj, type.isArray()? type.cast(value): value);
    }

}