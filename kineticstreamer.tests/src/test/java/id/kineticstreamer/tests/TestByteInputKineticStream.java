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
package id.kineticstreamer.tests;

import id.kineticstreamer.streams.ByteInputKineticStream;
import java.io.DataInput;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestByteInputKineticStream extends ByteInputKineticStream {
    List<String> annotations = new ArrayList<>();

    public TestByteInputKineticStream(DataInput in) {
        super(in);
    }

    @Override
    public int readInt(Annotation[] fieldAnnotations) throws IOException {
        var ret = super.readInt(fieldAnnotations);
        add(ret, fieldAnnotations);
        return ret;
    }

    @Override
    public String readString(Annotation[] fieldAnnotations) throws IOException {
        var ret = super.readString(fieldAnnotations);
        add(ret, fieldAnnotations);
        return ret;
    }

    private void add(Object value, Annotation[] fieldAnnotations) {
        Arrays.stream(fieldAnnotations)
                .map(a -> "%s=%s".formatted(value, a.annotationType().getSimpleName()))
                .forEach(annotations::add);
    }
}
