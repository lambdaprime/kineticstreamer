package id.kineticstreamer;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

enum KineticStreamTypes {

    STRING("java.lang.String"),
    INT("int"),
    INT_WRAPPER("java.lang.Integer"),
    LONG("long"),
    LONG_WRAPPER("java.lang.Long"),
    SHORT("short"),
    SHORT_WRAPPER("java.lang.Short"),
    FLOAT("float"),
    FLOAT_WRAPPER("java.lang.Float"),
    DOUBLE("double"),
    DOUBLE_WRAPPER("java.lang.Double"),
    BYTE("byte"),
    BYTE_WRAPPER("java.lang.Byte"),
    BOOL("boolean"),
    BOOL_WRAPPER("java.lang.Boolean");

    public static final Map<String, KineticStreamTypes> TYPE_NAME_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(k -> k.type, v -> v));
    public static final EnumSet<KineticStreamTypes> WRAPPERS = EnumSet.of(
        INT_WRAPPER, LONG_WRAPPER, SHORT_WRAPPER, FLOAT_WRAPPER, DOUBLE_WRAPPER, BYTE_WRAPPER,
        BOOL_WRAPPER);
    private String type;

    KineticStreamTypes(String type) {
        this.type = type;
    }
    
    public String getTypeName() {
        return type;
    }

    boolean isWrapper() {
        return WRAPPERS.contains(this);
    }
}
