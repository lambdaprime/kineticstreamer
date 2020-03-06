package id.kineticstreamer.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import id.kineticstreamer.annotations.Streamed;

public class KineticUtils {

    public Stream<Field> findStreamedFields(Object b) {
        return Arrays.stream(b.getClass().getFields())
                .filter(f -> f.getAnnotationsByType(Streamed.class) != null);
    }
}
