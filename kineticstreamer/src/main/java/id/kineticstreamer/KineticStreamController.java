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

import java.util.Optional;

/**
 * Allows user to control behavior of {@link KineticStreamReader} and {@link KineticStreamWriter}.
 *
 * <p>Thread-safety of this class depends on {@link StreamedFieldsProvider} being used. If {@link
 * StreamedFieldsProvider} implementation is thread-safe then this class is thread-safe too.
 *
 * <p>Thread-safety of this class depends on {@link StreamedFieldsProvider} being used. If {@link
 * StreamedFieldsProvider} implementation is thread-safe then this class is thread-safe too.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class KineticStreamController {

    private StreamedFieldsProvider fieldsProvider = new PublicStreamedFieldsProvider();

    public KineticStreamController withFieldsProvider(StreamedFieldsProvider fieldsProvider) {
        this.fieldsProvider = fieldsProvider;
        return this;
    }

    public StreamedFieldsProvider getFieldsProvider() {
        return fieldsProvider;
    }

    public record WriterResult(boolean skip) {

        /** Tell {@link KineticStreamWriter} to continue writing the field */
        public static final WriterResult CONTINUE = new WriterResult();

        public WriterResult() {
            this(false);
        }
    }

    /**
     * Decide whether to skip writing next field or not as well as perform custom serialization.
     *
     * <p>By overriding this method users can write object manually.
     *
     * @param obj object from the field which is about to be serialized
     */
    public WriterResult onNextObject(OutputKineticStream out, Object obj) throws Exception {
        return WriterResult.CONTINUE;
    }

    public record ReaderResult(boolean skip, Optional<Object> object) {

        /** Tell {@link KineticStreamReader} to continue reading the field */
        public static final ReaderResult CONTINUE = new ReaderResult();

        public ReaderResult() {
            this(false, Optional.empty());
        }

        public ReaderResult(boolean skip) {
            this(skip, Optional.empty());
        }

        public ReaderResult(boolean skip, Object obj) {
            this(skip, Optional.ofNullable(obj));
        }
    }

    /**
     * Decide whether to skip reading next field or not as well as perform custom deserialization.
     *
     * <p>By overriding this method users can read object manually and return it back to {@link
     * KineticStreamReader} which will store it as a field of the deserialized object.
     *
     * <p>Please note that when {@link KineticStreamReader#read(Class)} is called then {@link
     * KineticStreamController#onNextObject(InputKineticStream, Object, Class)} is issued for that
     * Class fields and not for the Class itself. For example read(Data.class) will not result to
     * onNextObject call with Data.class as a fieldType but to Data fields instead (this is needed
     * to avoid possible recursion in case users try to call read(Data.class) from onNextObject,
     * especially this is relevant when trying to read Data[]).
     *
     * @param obj object from the field which is about to be deserialized
     * @param fieldType type of the field which {@link KineticStreamReader} is about to deserialize
     */
    public ReaderResult onNextObject(InputKineticStream in, Object obj, Class<?> fieldType)
            throws Exception {
        return ReaderResult.CONTINUE;
    }
}
