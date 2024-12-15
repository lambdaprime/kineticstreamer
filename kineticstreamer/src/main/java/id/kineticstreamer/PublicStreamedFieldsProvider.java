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

import id.xfunction.Preconditions;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Streamed fields provider which returns only public fields. Any static, private, final, transient
 * fields are ignored.
 *
 * <p>By default fields are sorted based on their names with {@link Comparator#naturalOrder()}.
 * Users can change the ordering of the streamed fields with {@link
 * #PublicStreamedFieldsProvider(Function)}
 *
 * <p>Thread-safety of this class depends on provided mapping function {@link
 * #PublicStreamedFieldsProvider(Function)}. If provided function is thread-safe then this class is
 * thread-safe too.
 *
 * @author lambdaprime intid@protonmail.com
 */
public class PublicStreamedFieldsProvider implements StreamedFieldsProvider {

    private Optional<Function<Class<?>, List<String>>> fieldsOrderedByNameProvider =
            Optional.empty();

    public PublicStreamedFieldsProvider() {}

    /**
     * @param fieldsOrderedByNameProvider function which returns ordered list of streamed field
     *     names for a particular class. Common implementation of this function is to read such list
     *     from the class level annotation or some special static field of that class.
     */
    public PublicStreamedFieldsProvider(
            Function<Class<?>, List<String>> fieldsOrderedByNameProvider) {
        this.fieldsOrderedByNameProvider = Optional.of(fieldsOrderedByNameProvider);
    }

    @Override
    public List<Field> getDeclaredStreamedFields(Class<?> clazz) {
        List<Field> out = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()))
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isTransient(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .forEach(out::add);
        if (out.size() > 1) {
            out.sort(
                    fieldsOrderedByNameProvider
                            .map(p -> p.apply(clazz))
                            .map(l -> createComparator(clazz, l))
                            .orElse(Comparator.comparing(Field::getName)));
        }
        out = List.copyOf(out);
        return out;
    }

    private Comparator<Field> createComparator(Class<?> clazz, List<String> orderedFieldNames) {
        var fieldsMap =
                IntStream.range(0, orderedFieldNames.size())
                        .boxed()
                        .collect(
                                Collectors.toMap(
                                        i -> orderedFieldNames.get(i), Function.identity()));
        BiConsumer<Integer, String> checkNotNull =
                (val, name) ->
                        Preconditions.notNull(
                                val,
                                "No field '%s' found inside defined list of stream fields %s of"
                                        + " class %s",
                                name,
                                orderedFieldNames,
                                clazz.getSimpleName());
        return (a, b) -> {
            var aName = a.getName();
            var aPos = fieldsMap.get(aName);
            checkNotNull.accept(aPos, aName);
            var bName = b.getName();
            var bPos = fieldsMap.get(bName);
            checkNotNull.accept(bPos, bName);
            return aPos - bPos;
        };
    }
}
