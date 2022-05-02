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

/**
 * Allows user to intercept and change {@link KineticStreamWriter} behavior.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class KineticStreamWriterController {

    public static class Result {
        boolean skip;

        /** Tell {@link KineticStreamWriter} to continue writing the field */
        public static final Result CONTINUE = new Result();

        public Result() {}

        public Result(boolean skip) {
            this.skip = skip;
        }
    }

    /**
     * Decide whether to skip writing next field or not as well as perform custom serialization.
     *
     * <p>By overriding this method users can write object manually.
     *
     * @param obj object from the field which is about to be serialized
     */
    public Result onNextObject(OutputKineticStream out, Object obj) throws Exception {
        return Result.CONTINUE;
    }
}
