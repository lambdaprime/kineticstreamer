package id.kineticstreamer;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import id.kineticstreamer.annotations.Streamed;
import id.xfunction.XUtils;

public class KineticUtils {

    /**
     * Creates an object of give type using its default ctor
     */
    public Object createObject(Class<?> type) {
        try {
            var ctor = type.getConstructor();
            if (ctor == null)
                XUtils.throwRuntime("Type %s has no default ctor",  type);
            return ctor.newInstance();
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for all fields annotated as Streamed in the clazz
     */
    public List<Field> findStreamedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getFields())
                .filter(f -> f.getAnnotationsByType(Streamed.class).length != 0)
                .collect(toList());
    }
}
