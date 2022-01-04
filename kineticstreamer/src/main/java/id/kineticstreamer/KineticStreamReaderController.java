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
 * Control how objects being read from the kinetic streams.
 * 
 * <p>It may be needed in case of complex deserialization logic.
 * For example when fields may be present in the stream or not
 * based on data which was previously read. 
 */
public class KineticStreamReaderController {

    public static class Result {
        boolean skip;
        Optional<Object> object = Optional.empty();

        /**
         * Tell <b>kineticstreamer</b> to continue reading the object
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
     * Decide whether to skip fields or not as well as perform custom
     * desirialization.
     * 
     * <p>By overriding this method users can read object manually and
     * return it back to <b>kineticstreamer</b> which will store it as
     * a field of the deserialized object.
     * 
     * @param obj object which being currently deserialized and which
     * field <b>kineticstreamer</b> is going to read next
     * @param fieldType type of the field which <b>kineticstreamer</b>
     * is about to read
     */
    public Result onNextObject(Object obj, Class<?> fieldType) throws Exception {
        return Result.CONTINUE;
    }

}
