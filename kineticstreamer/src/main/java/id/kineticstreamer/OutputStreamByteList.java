package id.kineticstreamer;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts all data which is written into, to a List of
 * string bytes so that writing "abc" will result to:
 * [61, 62, 63] and "hello" to [68, 65, 6c, 6c, 6f]
 */
public class OutputStreamByteList extends OutputStream {

    public List<Integer> output = new ArrayList<>();
    
    @Override
    public void write(int i) throws IOException {
        output.add(i);
    }

    public String asHexString() {
        return output.stream()
                .map(i -> String.format("%2s", Integer.toHexString(i)))
                .map(s -> s.replace(" ", "0"))
                .collect(joining(", "));
    }
}
