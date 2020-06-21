package id.kineticstreamer.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import id.kineticstreamer.annotations.Streamed;

public class KineticUtils {

    public Stream<Field> findStreamedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getFields())
                .filter(f -> f.getAnnotationsByType(Streamed.class).length != 0);
    }
}
