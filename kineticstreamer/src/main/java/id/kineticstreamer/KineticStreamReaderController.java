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
/*
 * Authors:
 * - lambdaprime <intid@protonmail.com>
 */
package id.kineticstreamer;

import java.util.Optional;

/**
 * Allows user to intercept and change {@link KineticStreamReader} behavior.
 */
public class KineticStreamReaderController {

    public static class Result {
        boolean skip;
        Optional<Object> object = Optional.empty();

        /**
         * Tell {@link KineticStreamReader} to continue reading the field
         */
        public static final Result CONTINUE = new Result();

        public Result() {

        }
        
        public Result(boolean skip) {
            this.skip = skip;
        }

        public Result(boolean skip, Object object) {
            this.skip = skip;
            this.object = Optional.of(object);
        }
    }
    
    /**
     * Decide whether to skip reading next field or not as well as perform custom
     * deserialization.
     * 
     * <p>By overriding this method users can read object manually and
     * return it back to {@link KineticStreamReader} which will store it as
     * a field of the deserialized object.
     * 
     * <p>Please note that when {@link KineticStreamReader#read(Class)} is called then
     * {@link KineticStreamReaderController#onNextObject(InputKineticStream, Object, Class)}
     * is issued for that Class fields and not for the Class itself. For example read(Data.class) will
     * not result to onNextObject call with Data.class as a fieldType but to Data fields instead (this is needed
     * to avoid possible recursion in case you try to call read(Data.class) from onNextObject).
     * 
     * @param obj object which being currently deserialized and which
     * field {@link KineticStreamReader} is going to read next
     * @param fieldType type of the field which {@link KineticStreamReader}
     * is about to read
     */
    public Result onNextObject(InputKineticStream in, Object obj, Class<?> fieldType) throws Exception {
        return Result.CONTINUE;
    }

}
